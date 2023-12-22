package com.enqbs.app.service.order;

import com.enqbs.app.convert.OrderConvert;
import com.enqbs.app.form.OrderConfirmForm;
import com.enqbs.app.form.OrderForm;
import com.enqbs.app.pojo.dto.SkuStockDTO;
import com.enqbs.app.pojo.vo.OrderShippingAddressVO;
import com.enqbs.app.pojo.vo.SkuVO;
import com.enqbs.app.pojo.vo.UserCouponVO;
import com.enqbs.app.service.product.SkuService;
import com.enqbs.app.service.user.CartService;
import com.enqbs.app.service.product.SpuService;
import com.enqbs.app.service.mq.RabbitMQService;
import com.enqbs.app.service.product.SkuStockService;
import com.enqbs.app.service.user.UserCouponService;
import com.enqbs.app.service.user.UserService;
import com.enqbs.app.service.user.UserShippingAddressService;
import com.enqbs.app.pojo.vo.CartProductVO;
import com.enqbs.app.pojo.vo.OrderConfirmVO;
import com.enqbs.app.pojo.vo.OrderItemVO;
import com.enqbs.app.pojo.vo.OrderVO;
import com.enqbs.app.pojo.vo.ProductVO;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.app.pojo.vo.UserShippingAddressVO;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
    private OrderShippingAddressService orderShippingAddressService;
    @Resource
    private UserService userService;
    @Resource
    private UserShippingAddressService userShippingAddressService;
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
        List<CartProductVO> cartProductVOList = cartService.getCartProductVOListBySelected();       // 购物车选中的商品

        if (CollectionUtils.isEmpty(cartProductVOList)) {
            throw new ServiceException("请选中商品再下单");
        }

        String orderToken = IDUtil.getUUID();       // 订单唯一凭证,保证接口幂等性
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        List<UserShippingAddressVO> userShippingAddressVOList = userShippingAddressService.getUserShippingAddressVOList();  // 用户收货地址列表
        List<UserCouponVO> userCouponVOList = userCouponService.getUserCouponVOList();                                      // 用户优惠券列表
        List<OrderItemVO> orderItemVOList = cartProductVOList.stream().map(this::cartProductVO2OrderItemVO).collect(Collectors.toList());
        OrderConfirmVO orderConfirmVO = buildOrderConfirmVO(orderToken, userShippingAddressVOList, userCouponVOList, orderItemVOList);
        /* 异步执行缓存任务 */
        executor.execute(() -> {
                    String redisKeyOrderToken = String.format(Constants.ORDER_TOKEN_REDIS_KEY, userInfoVO.getUserId());
                    String redisKeyOrderConfirm = String.format(Constants.ORDER_CONFIRM_REDIS_KEY, userInfoVO.getUserId());
                    redisUtil.setString(redisKeyOrderToken, orderToken, Long.valueOf(ORDER_TIMEOUT));
                    redisUtil.setString(redisKeyOrderConfirm, GsonUtil.obj2Json(orderConfirmVO), Long.valueOf(ORDER_TIMEOUT));
                }
        );
        return orderConfirmVO;
    }

    @Override
    public OrderConfirmVO getOrderConfirmVO(OrderConfirmForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        String redisKey = String.format(Constants.ORDER_TOKEN_REDIS_KEY, userInfoVO.getUserId());
        Long result = redisUtil.executeScript(Constants.REDIS_SCRIPT, redisKey, form.getOrderToken());

        if (result == 0) {
            throw new ServiceException("订单凭证失效,请刷新页面");
        }

        String redisKeyOrderToken = String.format(Constants.ORDER_TOKEN_REDIS_KEY, userInfoVO.getUserId());
        String redisKeyOrderConfirm = String.format(Constants.ORDER_CONFIRM_REDIS_KEY, userInfoVO.getUserId());
        OrderConfirmVO orderConfirmVO = GsonUtil.json2Obj(redisUtil.getString(redisKeyOrderConfirm), OrderConfirmVO.class);

        if (ObjectUtils.isNotEmpty(form.getCouponsId())) {
            try {
                UserCouponVO userCouponVO = userCouponService.getUserCouponVO(form.getCouponsId());

                if (Constants.COUPON_USED.equals(userCouponVO.getStatus())
                        || Constants.COUPON_INVALID.equals(userCouponVO.getCoupon().getStatus())
                        || new Date().after(userCouponVO.getCoupon().getEndDate())) {
                    throw new ServiceException("优惠券已失效");
                }

                if (orderConfirmVO.getAmount().compareTo(userCouponVO.getCoupon().getCondition()) > -1) {
                    orderConfirmVO.setCouponId(form.getCouponsId());
                    orderConfirmVO.setCouponAmount(userCouponVO.getCoupon().getDenomination());
                    orderConfirmVO.setActualAmount(orderConfirmVO.getAmount().subtract(userCouponVO.getCoupon().getDenomination()));
                } else {
                    throw new ServiceException("订单未满足优惠条件");
                }
            } catch (Exception e) {
                redisUtil.setString(redisKeyOrderToken, form.getOrderToken(), Long.valueOf(ORDER_TIMEOUT));
                redisUtil.setString(redisKeyOrderConfirm, GsonUtil.obj2Json(orderConfirmVO), Long.valueOf(ORDER_TIMEOUT));
                throw e;
            }
        } else {
            orderConfirmVO.setCouponAmount(BigDecimal.ZERO);
            orderConfirmVO.setActualAmount(orderConfirmVO.getAmount());
        }

        executor.execute(() -> {
                    redisUtil.setString(redisKeyOrderToken, form.getOrderToken(), Long.valueOf(ORDER_TIMEOUT));
                    redisUtil.setString(redisKeyOrderConfirm, GsonUtil.obj2Json(orderConfirmVO), Long.valueOf(ORDER_TIMEOUT));
                }
        );
        return orderConfirmVO;
    }

    @Override
    public Long submit(OrderForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        String redisKeyOrderToken = String.format(Constants.ORDER_TOKEN_REDIS_KEY, userInfoVO.getUserId());
        Long result = redisUtil.executeScript(Constants.REDIS_SCRIPT, redisKeyOrderToken, form.getOrderToken());

        if (result == 0) {
            throw new ServiceException("订单凭证失效,请刷新页面");
        }

        long orderNo = IDUtil.getId();
        List<OrderItem> orderItemList = new ArrayList<>();      // 订单项
        List<SkuStockDTO> stockList = new ArrayList<>();        // 锁定商品库存列表
        /* 缓存获取预生成的订单信息 */
        String redisKeyOrderConfirm = String.format(Constants.ORDER_CONFIRM_REDIS_KEY, userInfoVO.getUserId());
        OrderConfirmVO orderConfirm = GsonUtil.json2Obj(redisUtil.getString(redisKeyOrderConfirm), OrderConfirmVO.class);
        List<OrderItemVO> orderItemVOList = orderConfirm.getOrderItemList();      // 获取预生成的订单项信息
        Set<Integer> productIdSet = orderItemVOList.stream().map(OrderItemVO::getSpuId).collect(Collectors.toSet());
        Set<Integer> skuIdSet = orderItemVOList.stream().map(OrderItemVO::getSkuId).collect(Collectors.toSet());
        /* List to Map */
        Map<Integer, ProductVO> productVOMap = spuService
                .getProductVOList(productIdSet).stream().collect(Collectors.toMap(ProductVO::getId, v -> v));               // 预生成订单项中的商品
        Map<Integer, SkuVO> skuVOMap = skuService
                .getSkuVOList(skuIdSet, Collections.emptySet()).stream().collect(Collectors.toMap(SkuVO::getId, v -> v));   // 商品规格
        Map<Integer, SkuStock> stockMap = skuStockService
                .getSkuStockList(skuIdSet).stream().collect(Collectors.toMap(SkuStock::getSkuId, v -> v));                  // 商品规格库存
        /* 验证库存是否充足 */
        for (OrderItemVO orderItemVO : orderItemVOList) {
            ProductVO productVO = productVOMap.get(orderItemVO.getSpuId());

            if (ObjectUtils.isEmpty(productVO)) {
                throw new ServiceException("商品ID:" + orderItemVO.getSpuId() + ",商品:" + orderItemVO.getSkuTitle() + ",下架或删除");
            }

            SkuVO skuVO = skuVOMap.get(orderItemVO.getSkuId());
            SkuStock stock = stockMap.get(orderItemVO.getSkuId());

            if (ObjectUtils.isEmpty(skuVO) || ObjectUtils.isEmpty(stock)) {
                throw new ServiceException("商品规格ID:" + orderItemVO.getSkuId() + ",商品:" + orderItemVO.getSkuTitle() + ",下架或删除");
            }

            if (orderItemVO.getNum() > stock.getStock()) {
                throw new ServiceException("商品规格ID:" + orderItemVO.getSkuId() + ",商品:" + orderItemVO.getSkuTitle() + ",库存不足");
            }

            OrderItem orderItem = orderConvert.orderItemVO2OrderItem(orderItemVO);
            orderItem.setOrderNo(orderNo);
            orderItem.setSkuParams(GsonUtil.obj2Json(orderItemVO.getSkuParams()));
            orderItemList.add(orderItem);
            SkuStockDTO skuStockDTO = getSkuStockDTO(stock, orderItemVO);
            stockList.add(skuStockDTO);
        }
        /* 订单收货地址信息 */
        UserShippingAddressVO userShippingAddressVO = userShippingAddressService.getUserShippingAddressVO(form.getShippingAddressId());
        OrderShippingAddress address = orderConvert.userShippingAddressVO2OrderShippingAddress(userShippingAddressVO);
        address.setOrderNo(orderNo);
        OrderService orderServiceProxy = (OrderService) AopContext.currentProxy();                  // 获取接口代理,解决本类方法调用事务失效问题
        orderServiceProxy.insert(orderNo, userInfoVO.getUserId(), form.getCouponId(), orderItemList, address, stockList, orderConfirm);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();     // 解决多线程丢失请求头问题
        executor.execute(() -> {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    cartService.deleteCartProductVOListBySelected();
                    redisUtil.deleteKey(redisKeyOrderConfirm);
                }
        );
        log.info("订单号:'{}',订单提交成功.", orderNo);
        return orderNo;
    }

    @Override
    public OrderVO getOrderVO(Long orderNo) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Order order = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(orderNo, userInfoVO.getUserId(),
                null, Constants.IS_NOT_DELETE);

        if (ObjectUtils.isEmpty(order)) {
            throw new ServiceException("订单号:" + orderNo + ",订单不存在");
        }

        OrderVO orderVO = orderConvert.order2OrderVO(order);
        OrderShippingAddressVO orderShippingAddressVO = orderShippingAddressService.getOrderShippingAddressVO(orderNo);
        List<OrderItemVO> orderItemVOList = orderItemService.getOrderItemVOList(orderNo);
        orderVO.setShippingAddress(orderShippingAddressVO);
        orderVO.setOrderItemList(orderItemVOList);
        return orderVO;
    }

    @Override
    public PageUtil<OrderVO> getOrderVOList(Integer status, SortEnum sort, Integer pageNum, Integer pageSize) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        PageUtil<OrderVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        List<Order> orderList = orderMapper.selectListByParam(null, null, userInfoVO.getUserId(), null,
                status, Constants.IS_NOT_DELETE, sort.getSortType(), pageNum, pageSize);

        if (CollectionUtils.isEmpty(orderList)) {
            return pageUtil;
        }

        Long total = orderMapper.countByParam(null, null, userInfoVO.getUserId(),
                null, status, Constants.IS_NOT_DELETE);
        Set<Long> orderNoSet = orderList.stream().map(Order::getOrderNo).collect(Collectors.toSet());
        Map<Long, List<OrderItemVO>> orderItemVOMap = orderItemService
                .getOrderItemVOList(orderNoSet).stream().collect(Collectors.groupingBy(OrderItemVO::getOrderNo));
        List<OrderVO> orderVOList = orderList.stream().map(e -> {
                    OrderVO orderVO = orderConvert.order2OrderVO(e);
                    orderVO.setOrderItemList(orderItemVOMap.get(orderVO.getOrderNo()));
                    return orderVO;
                }
        ).collect(Collectors.toList());
        pageUtil.setTotal(total);
        pageUtil.setList(orderVOList);
        return pageUtil;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(Long orderNo, Integer userId, Integer couponId, List<OrderItem> orderItemList,
                       OrderShippingAddress address, List<SkuStockDTO> stockList, OrderConfirmVO orderConfirm) {
        skuStockService.lockSkuStock(orderNo, stockList);       // 锁定库存(此处 SQL 乐观锁处理)
        BigDecimal amount = orderConfirm.getAmount();           // 订单金额
        Order order;                                            // 生成订单信息
        /* 是否使用优惠券、验证优惠券有效性 */
        if (ObjectUtils.isNotEmpty(couponId)) {
            if (!couponId.equals(orderConfirm.getCouponId())) {
                throw new ServiceException("当前优惠券与下单选中优惠券不一致");
            }

            userCouponService.deduct(orderNo, couponId);        // 扣除用户优惠券
            order = buildOrder(orderNo, userId, couponId, orderConfirm.getPostage(),
                    amount, orderConfirm.getCouponAmount(), orderConfirm.getDiscountAmount());
        } else {
            order = buildOrder(orderNo, userId, couponId, orderConfirm.getPostage(), amount, BigDecimal.ZERO, BigDecimal.ZERO);
        }
        /* 保存订单信息 */
        orderItemService.batchInsert(orderNo, orderItemList);
        orderShippingAddressService.insert(orderNo, address);
        insert(orderNo, order);
        /* 发送延迟消息,15分钟订单未支付自动关闭 */
        rabbitMQService.send(QueueEnum.ORDER_CLOSE_QUEUE, orderNo, ORDER_TIMEOUT);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sign(Long orderNo) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Order order = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(orderNo, userInfoVO.getUserId(),
                OrderStatusEnum.NOT_RECEIPT.getCode(), Constants.IS_NOT_DELETE);

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
        Order order = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(orderNo, userInfoVO.getUserId(),
                OrderStatusEnum.NOT_PAY.getCode(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isEmpty(order)) {
            throw new ServiceException("订单号:" + orderNo + ",无法取消该订单");
        }

        skuStockService.unLockSkuStock(orderNo, OrderStatusEnum.NOT_PAY);

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
        Order order = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(orderNo, null,
                OrderStatusEnum.NOT_PAY.getCode(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isNotEmpty(order) && ObjectUtils.isEmpty(order.getConsumeVersion())) {
            skuStockService.unLockSkuStock(order.getOrderNo(), OrderStatusEnum.TIMEOUT);

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
        Order order = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(orderNo, null,
                OrderStatusEnum.NOT_PAY.getCode(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isNotEmpty(order) && ObjectUtils.isEmpty(order.getConsumeVersion())) {
            skuStockService.unLockSkuStock(orderNo, OrderStatusEnum.PAY_SUCCESS);
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

    private void insert(Long orderNo, Order order) {
        int row = orderMapper.insertSelective(order);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",订单信息保存失败");
        }
    }

    private Order buildOrder(Long orderNo, Integer userId, Integer couponId,
                             BigDecimal postage, BigDecimal amount, BigDecimal couponAmount, BigDecimal discountAmount) {
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);

        if (ObjectUtils.isNotEmpty(couponId)) {
            order.setCouponId(couponId);
        }

        order.setPostage(postage);                                  // 订单运费
        order.setAmount(amount);                                    // 订单金额
        order.setCouponAmount(couponAmount);                        // 订单优惠券金额
        order.setDiscountAmount(discountAmount);                    // 订单优惠金额
        order.setActualAmount(amount.subtract(couponAmount));       // 订单实付金额
        return order;
    }

    private OrderConfirmVO buildOrderConfirmVO(String orderToken, List<UserShippingAddressVO> userShippingAddressVOList,
                                               List<UserCouponVO> userCouponVOList, List<OrderItemVO> orderItemVOList) {
        OrderConfirmVO orderConfirmVO = new OrderConfirmVO();
        BigDecimal amount = orderItemVOList.stream().map(OrderItemVO::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        if (!CollectionUtils.isEmpty(userShippingAddressVOList)) {
            orderConfirmVO.setShippingAddressList(userShippingAddressVOList);
        }
        /* 筛选满足优惠券条件的优惠券 */
        if (!CollectionUtils.isEmpty(userCouponVOList)) {
            List<UserCouponVO> userCouponVOS = userCouponVOList.stream()
                    .filter(e -> amount.compareTo(e.getCoupon().getCondition()) > -1)
                    .filter(e -> new Date().before(e.getCoupon().getEndDate()))
                    .collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(userCouponVOS)) {
                orderConfirmVO.setCouponList(userCouponVOS);    // 满足优惠条件的优惠券列表
            }
        }

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

    private SkuStockDTO getSkuStockDTO(SkuStock stock, OrderItemVO orderItemVO) {
        SkuStockDTO skuStockDTO = new SkuStockDTO();
        skuStockDTO.setSkuId(orderItemVO.getSkuId());
        skuStockDTO.setSpuId(orderItemVO.getSpuId());
        skuStockDTO.setSkuStockId(stock.getId());
        skuStockDTO.setLockStock(orderItemVO.getNum());
        skuStockDTO.setStock(orderItemVO.getNum());
        skuStockDTO.setQuantity(orderItemVO.getNum());
        return skuStockDTO;
    }

}
