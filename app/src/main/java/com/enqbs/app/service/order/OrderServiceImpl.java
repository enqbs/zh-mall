package com.enqbs.app.service.order;

import com.enqbs.app.convert.OrderConvert;
import com.enqbs.app.form.OrderConfirmForm;
import com.enqbs.app.form.OrderForm;
import com.enqbs.app.pojo.dto.SkuStockDTO;
import com.enqbs.app.pojo.vo.SkuVO;
import com.enqbs.app.pojo.vo.UserCouponVO;
import com.enqbs.app.service.product.SkuService;
import com.enqbs.app.service.user.CartService;
import com.enqbs.app.service.product.SpuService;
import com.enqbs.app.service.mq.RabbitMQService;
import com.enqbs.app.service.product.SkuStockService;
import com.enqbs.app.service.user.UserCouponService;
import com.enqbs.app.service.user.UserService;
import com.enqbs.app.service.user.UserAddressService;
import com.enqbs.app.pojo.vo.CartProductVO;
import com.enqbs.app.pojo.vo.OrderConfirmVO;
import com.enqbs.app.pojo.vo.OrderItemVO;
import com.enqbs.app.pojo.vo.OrderVO;
import com.enqbs.app.pojo.vo.ProductVO;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.app.pojo.vo.UserAddressVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.app.enums.OrderStatusEnum;
import com.enqbs.app.enums.QueueEnum;
import com.enqbs.app.enums.SortEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.common.util.IDUtil;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.RedisUtil;
import com.enqbs.generator.dao.OrderMapper;
import com.enqbs.generator.pojo.Order;
import com.enqbs.generator.pojo.OrderItem;
import com.enqbs.generator.pojo.OrderShippingAddress;
import com.enqbs.generator.pojo.SkuStock;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private OrderItemService orderItemService;
    @Resource
    private OrderAddressService orderAddressService;
    @Resource
    private UserService userService;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private UserCouponService userCouponService;
    @Resource
    private CartService cartService;
    @Resource
    private SpuService spuService;
    @Resource
    private SkuService skuService;
    @Resource
    private SkuStockService skuStockService;
    @Resource
    private RabbitMQService rabbitMQService;
    @Resource
    private OrderConvert orderConvert;
    @Resource
    private ThreadPoolTaskExecutor executor;

    private static final Integer ORDER_TIMEOUT = 900000;

    @Override
    public OrderConfirmVO getOrderConfirmVO() {
        List<CartProductVO> cartProductVOList = cartService.getCartProductVOListBySelected();

        if (CollectionUtils.isEmpty(cartProductVOList)) {
            throw new ServiceException("请选中商品再下单");
        }

        String orderToken = IDUtil.getUUID();       // 订单唯一凭证,保证接口幂等性
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        List<UserAddressVO> addressVOList = userAddressService.getAddressVOList();
        List<UserCouponVO> couponVOList = userCouponService.getCouponVOList();
        List<OrderItemVO> orderItemVOList = cartProductVOList.stream().map(this::cartProductVO2OrderItemVO).toList();
        OrderConfirmVO orderConfirmVO = buildOrderConfirmVO(orderToken, addressVOList, couponVOList, orderItemVOList);
        Thread.ofVirtual().name("orderConfirm-cacheOrderToken").start(() -> {
                    String orderTokenKey = String.format(Constants.ORDER_TOKEN_REDIS_KEY, userInfoVO.getUserId());
                    String orderConfirmVOKey = String.format(Constants.ORDER_CONFIRM_REDIS_KEY, userInfoVO.getUserId());
                    redisUtil.setString(orderTokenKey, orderToken, Long.valueOf(ORDER_TIMEOUT));
                    redisUtil.setString(orderConfirmVOKey, GsonUtil.obj2Json(orderConfirmVO), Long.valueOf(ORDER_TIMEOUT));
                }
        );
        return orderConfirmVO;
    }

    @Override
    public OrderConfirmVO getOrderConfirmVO(OrderConfirmForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Long result = redisUtil.executeScript(Constants.REDIS_SCRIPT, String.format(Constants.ORDER_TOKEN_REDIS_KEY, userInfoVO.getUserId()), form.getOrderToken());

        if (result == 0) {
            throw new ServiceException("订单凭证失效,请刷新页面");
        }

        String orderTokenKey = String.format(Constants.ORDER_TOKEN_REDIS_KEY, userInfoVO.getUserId());
        String orderConfirmVOKey = String.format(Constants.ORDER_CONFIRM_REDIS_KEY, userInfoVO.getUserId());
        OrderConfirmVO orderConfirmVO = GsonUtil.json2Obj(redisUtil.getString(orderConfirmVOKey), OrderConfirmVO.class);
        UserCouponVO couponVO = userCouponService.getCouponVO(form.getCouponsId());

        try {
            if (Constants.COUPON_USED.equals(couponVO.getStatus())
                    || Constants.COUPON_INVALID.equals(couponVO.getCoupon().getStatus())
                    || new Date().after(couponVO.getCoupon().getEndDate())) {
                throw new ServiceException("优惠券已失效");
            }

            if (orderConfirmVO.getAmount().compareTo(couponVO.getCoupon().getCondition()) > -1) {
                orderConfirmVO.setCouponId(form.getCouponsId());
                orderConfirmVO.setCouponAmount(couponVO.getCoupon().getDenomination());
                orderConfirmVO.setActualAmount(orderConfirmVO.getAmount().subtract(couponVO.getCoupon().getDenomination()));
            } else {
                throw new ServiceException("订单未满足优惠条件");
            }
        } finally {
            Thread.ofVirtual().name("orderConfirm-updateOrderToken").start(() -> {
                        redisUtil.setString(orderTokenKey, form.getOrderToken(), Long.valueOf(ORDER_TIMEOUT));
                        redisUtil.setString(orderConfirmVOKey, GsonUtil.obj2Json(orderConfirmVO), Long.valueOf(ORDER_TIMEOUT));
                    }
            );
        }

        return orderConfirmVO;
    }

    @Override
    public Long submit(OrderForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Long result = redisUtil.executeScript(Constants.REDIS_SCRIPT, String.format(Constants.ORDER_TOKEN_REDIS_KEY, userInfoVO.getUserId()), form.getOrderToken());

        if (result == 0) {
            throw new ServiceException("订单凭证失效,请刷新页面");
        }

        String orderConfirmVOKey = String.format(Constants.ORDER_CONFIRM_REDIS_KEY, userInfoVO.getUserId());
        OrderConfirmVO orderConfirmVO = GsonUtil.json2Obj(redisUtil.getString(orderConfirmVOKey), OrderConfirmVO.class);    // 缓存获取预生成的订单信息
        Thread.ofVirtual().name("orderSubmit-deleteOrderConfirm").start(() -> redisUtil.deleteKey(orderConfirmVOKey));      // 清楚缓存信息
        long orderNo = IDUtil.getId();                                              // 订单号
        List<SkuStockDTO> stockList = new ArrayList<>();                            // 锁定商品库存列表
        List<OrderItemVO> orderItemVOList = orderConfirmVO.getOrderItemList();      // 获取预生成的订单项信息
        Set<Integer> spuIdSet = orderItemVOList.stream().map(OrderItemVO::getSpuId).collect(Collectors.toSet());
        Set<Integer> skuIdSet = orderItemVOList.stream().map(OrderItemVO::getSkuId).collect(Collectors.toSet());
        /* List to Map */
        Map<Integer, ProductVO> productVOMap = spuService.getProductVOList(spuIdSet).stream().collect(Collectors.toMap(ProductVO::getId, v -> v));
        Map<Integer, SkuVO> skuVOMap = skuService.getSkuVOList(skuIdSet, Collections.emptySet()).stream().collect(Collectors.toMap(SkuVO::getId, v -> v));
        Map<Integer, SkuStock> stockMap = skuStockService.getStockList(skuIdSet).stream().collect(Collectors.toMap(SkuStock::getSkuId, v -> v));
        List<OrderItem> orderItemList = orderItemVOList.stream().map(ovo -> {
                    ProductVO productVO = productVOMap.get(ovo.getSpuId());
                    SkuVO skuVO = skuVOMap.get(ovo.getSkuId());
                    SkuStock stock = stockMap.get(ovo.getSkuId());

                    if (ObjectUtils.isEmpty(productVO) || ObjectUtils.isEmpty(skuVO) || ObjectUtils.isEmpty(stock)) {
                        throw new ServiceException("商品ID:" + ovo.getSpuId() + "商品规格ID:" + ovo.getSkuId() + ",商品:" + ovo.getSkuTitle() + ",下架或删除");
                    }

                    if (ovo.getNum() > stock.getStock()) {
                        throw new ServiceException("商品ID:" + ovo.getSpuId() + "商品规格ID:" + ovo.getSkuId() + ",商品:" + ovo.getSkuTitle() + ",库存不足");
                    }

                    stockList.add(buildSkuStockDTO(stock, ovo));
                    OrderItem orderItem = orderConvert.orderItemVO2OrderItem(ovo);
                    orderItem.setOrderNo(orderNo);
                    orderItem.setSkuParams(GsonUtil.obj2Json(ovo.getSkuParams()));
                    return orderItem;
                }
        ).toList();
        /* 订单收货地址信息 */
        UserAddressVO userAddressVO = userAddressService.getAddressVO(form.getShippingAddressId());
        OrderShippingAddress orderAddress = orderConvert.userAddressVO2OrderAddress(userAddressVO);
        orderAddress.setOrderNo(orderNo);
        /* 获取接口代理,解决本类方法调用事务失效问题 */
        OrderService orderServiceProxy = (OrderService) AopContext.currentProxy();
        orderServiceProxy.insert(orderNo, userInfoVO.getUserId(), form.getCouponId(), orderItemList, orderAddress, stockList, orderConfirmVO);
        /* 解决多线程丢失请求头问题 */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        executor.execute(() -> {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    cartService.deleteCartProductVOListBySelected();
                }
        );
        log.info("订单号:'{}',订单提交成功.", orderNo);
        return orderNo;
    }

    @Override
    public OrderVO getOrderVO(Long orderNo) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Order order = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(orderNo, userInfoVO.getUserId(), null, Constants.IS_NOT_DELETE);

        if (ObjectUtils.isEmpty(order)) {
            throw new ServiceException("订单号:" + orderNo + ",订单不存在");
        }

        OrderVO orderVO = orderConvert.order2OrderVO(order);
        orderVO.setAddress(orderAddressService.getAddressVO(orderNo));
        orderVO.setOrderItemList(orderItemService.getOrderItemVOList(orderNo));
        return orderVO;
    }

    @Override
    public PageUtil<OrderVO> orderVOListPage(Integer status, SortEnum sort, Integer pageNum, Integer pageSize) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Long total = orderMapper.countByParam(null, null, userInfoVO.getUserId(), null, status, Constants.IS_NOT_DELETE);
        List<Order> orderList = orderMapper.selectListByParam(null, null, userInfoVO.getUserId(), null, status, Constants.IS_NOT_DELETE, sort.getSortType(), pageNum, pageSize);
        Set<Long> orderNoSet = orderList.stream().map(Order::getOrderNo).collect(Collectors.toSet());
        Map<Long, List<OrderItemVO>> orderItemVOListMap = orderItemService.getOrderItemVOList(orderNoSet).stream().collect(Collectors.groupingBy(OrderItemVO::getOrderNo));
        List<OrderVO> orderVOList = orderList.stream().map(o -> {
                    OrderVO orderVO = orderConvert.order2OrderVO(o);
                    orderVO.setOrderItemList(orderItemVOListMap.get(orderVO.getOrderNo()));
                    return orderVO;
                }
        ).toList();
        PageUtil<OrderVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        pageUtil.setTotal(total);
        pageUtil.setList(orderVOList);
        return pageUtil;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(Long orderNo, Integer userId, Integer couponId, List<OrderItem> orderItemList,
                       OrderShippingAddress address, List<SkuStockDTO> stockList, OrderConfirmVO orderConfirmVO) {
        skuStockService.lockStock(orderNo, stockList);      // 锁定库存(此处 SQL 乐观锁处理)

        if (ObjectUtils.isNotEmpty(couponId)) {
            userCouponService.deduct(orderNo, couponId);    // 扣除用户优惠券
        }

        Order order = new Order();
        order.setOrderNo(orderNo);                                          // 订单号
        order.setUserId(userId);                                            // 用户 ID
        order.setCouponId(couponId);                                        // 优惠券 ID
        order.setPostage(orderConfirmVO.getPostage());                      // 订单运费
        order.setAmount(orderConfirmVO.getAmount());                        // 订单金额
        order.setCouponAmount(orderConfirmVO.getCouponAmount());            // 订单优惠券金额
        order.setDiscountAmount(orderConfirmVO.getDiscountAmount());        // 订单优惠金额
        order.setActualAmount(orderConfirmVO.getActualAmount());            // 订单实付金额
        orderAddressService.insert(orderNo, address);
        orderItemService.batchInsert(orderNo, orderItemList);
        int row = orderMapper.insertSelective(order);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",订单信息保存失败");
        }

        rabbitMQService.send(QueueEnum.ORDER_CLOSE_QUEUE, orderNo, ORDER_TIMEOUT);      // 延迟消息,15分钟订单未支付自动关闭
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sign(Long orderNo) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Order order = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(orderNo, userInfoVO.getUserId(), OrderStatusEnum.NOT_RECEIPT.getCode(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isEmpty(order)) {
            throw new ServiceException("订单号:" + orderNo + ",无法签收该订单");
        }

        order.setStatus(OrderStatusEnum.RECEIPT_SUCCESS.getCode());
        order.setSignReceiptTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",订单签收失败");
        }

        log.info("订单号'{}',订单签收成功.", orderNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(Long orderNo) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Order order = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(orderNo, userInfoVO.getUserId(), OrderStatusEnum.NOT_PAY.getCode(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isEmpty(order)) {
            throw new ServiceException("订单号:" + orderNo + ",无法取消该订单");
        }

        skuStockService.unLockStock(orderNo, OrderStatusEnum.NOT_PAY);

        if (ObjectUtils.isNotEmpty(order.getCouponId())) {
            userCouponService.rollback(orderNo, order.getCouponId());
        }

        order.setDeleteStatus(Constants.IS_DELETE);
        int row = orderMapper.updateByPrimaryKeySelective(order);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",订单取消失败");
        }

        log.info("订单号:'{}',订单取消成功.", orderNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleTimeoutOrder(Long orderNo) {
        Order order = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(orderNo, null, OrderStatusEnum.NOT_PAY.getCode(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isNotEmpty(order) && ObjectUtils.isEmpty(order.getConsumeVersion())) {
            skuStockService.unLockStock(order.getOrderNo(), OrderStatusEnum.TIMEOUT);

            if (ObjectUtils.isNotEmpty(order.getCouponId())) {
                userCouponService.rollback(order.getOrderNo(), order.getCouponId());
            }

            order.setStatus(OrderStatusEnum.TIMEOUT.getCode());
            order.setDeleteStatus(Constants.IS_DELETE);
            order.setConsumeVersion(1);
            int row = orderMapper.updateByPrimaryKeySelective(order);

            if (row <= 0) {
                throw new ServiceException("订单号:" + orderNo + ",订单关闭失败");
            }

            log.info("订单号:'{}',订单关闭成功.", orderNo);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlePaySuccessOrder(Long orderNo) {
        Order order = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(orderNo, null, OrderStatusEnum.NOT_PAY.getCode(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isNotEmpty(order) && ObjectUtils.isEmpty(order.getConsumeVersion())) {
            skuStockService.unLockStock(orderNo, OrderStatusEnum.PAY_SUCCESS);
            order.setStatus(OrderStatusEnum.PAY_SUCCESS.getCode());
            order.setConsumeVersion(1);
            order.setPaymentTime(new Date());
            int row = orderMapper.updateByPrimaryKeySelective(order);

            if (row <= 0) {
                throw new ServiceException("订单号:" + orderNo + ",订单确认支付失败");
            }

            log.info("订单号:'{}',订单确认支付成功.", orderNo);
        }
    }

    private OrderConfirmVO buildOrderConfirmVO(String orderToken, List<UserAddressVO> addressVOList,
                                               List<UserCouponVO> couponVOList, List<OrderItemVO> orderItemVOList) {
        OrderConfirmVO orderConfirmVO = new OrderConfirmVO();
        BigDecimal amount = orderItemVOList.stream().map(OrderItemVO::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        couponVOList = CollectionUtils.isEmpty(couponVOList) ? Collections.emptyList() : couponVOList.stream()
                .filter(cvo -> amount.compareTo(cvo.getCoupon().getCondition()) > -1)
                .filter(cvo -> new Date().before(cvo.getCoupon().getEndDate())).toList();       // 筛选满足优惠券条件的优惠券
        orderConfirmVO.setCouponList(couponVOList);             // 优惠券列表
        orderConfirmVO.setShippingAddressList(addressVOList);   // 用户收货地址列表
        orderConfirmVO.setOrderItemList(orderItemVOList);       // 订单项列表
        orderConfirmVO.setPostage(BigDecimal.ZERO);             // 订单运费
        orderConfirmVO.setAmount(amount);                       // 订单金额
        orderConfirmVO.setCouponAmount(BigDecimal.ZERO);        // 订单优惠券金额
        orderConfirmVO.setDiscountAmount(BigDecimal.ZERO);      // 订单会员折扣金额
        orderConfirmVO.setActualAmount(amount);                 // 订单实付金额
        orderConfirmVO.setOrderToken(orderToken);               // 订单唯一凭证（接口幂等性）
        return orderConfirmVO;
    }

    private OrderItemVO cartProductVO2OrderItemVO(CartProductVO cartProductVO) {
        OrderItemVO orderItemVO = new OrderItemVO();
        orderItemVO.setSkuId(cartProductVO.getSkuId());
        orderItemVO.setSpuId(cartProductVO.getSpuId());
        orderItemVO.setSkuTitle(cartProductVO.getSkuTitle());
        orderItemVO.setSkuParams(cartProductVO.getParams());
        orderItemVO.setSkuPicture(cartProductVO.getPicture());
        orderItemVO.setNum(cartProductVO.getQuantity());
        orderItemVO.setPrice(cartProductVO.getPrice());
        orderItemVO.setTotalPrice(cartProductVO.getTotalPrice());
        return orderItemVO;
    }

    private SkuStockDTO buildSkuStockDTO(SkuStock stock, OrderItemVO orderItemVO) {
        SkuStockDTO stockDTO = new SkuStockDTO();
        stockDTO.setSkuId(orderItemVO.getSkuId());
        stockDTO.setSpuId(orderItemVO.getSpuId());
        stockDTO.setSkuStockId(stock.getId());
        stockDTO.setLockStock(orderItemVO.getNum());
        stockDTO.setStock(orderItemVO.getNum());
        stockDTO.setQuantity(orderItemVO.getNum());
        return stockDTO;
    }

}
