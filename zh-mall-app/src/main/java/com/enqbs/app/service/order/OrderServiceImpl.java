package com.enqbs.app.service.order;

import com.enqbs.app.form.OrderConfirmForm;
import com.enqbs.app.form.OrderForm;
import com.enqbs.app.pojo.dto.SkuStockDTO;
import com.enqbs.app.pojo.vo.OrderShippingAddressVO;
import com.enqbs.app.pojo.vo.UserCouponVO;
import com.enqbs.app.service.user.CartService;
import com.enqbs.app.service.product.ProductService;
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
import com.enqbs.common.enums.OrderStatusEnum;
import com.enqbs.common.enums.QueueEnum;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.IDUtil;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.RedisUtil;
import com.enqbs.generator.dao.OrderItemMapper;
import com.enqbs.generator.dao.OrderMapper;
import com.enqbs.generator.dao.OrderShippingAddressMapper;
import com.enqbs.generator.pojo.Order;
import com.enqbs.generator.pojo.OrderItem;
import com.enqbs.generator.pojo.OrderShippingAddress;
import com.enqbs.generator.pojo.PayInfo;
import com.enqbs.generator.pojo.Sku;
import com.enqbs.generator.pojo.SkuStock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private OrderItemMapper orderItemMapper;
    @Resource
    private OrderShippingAddressMapper orderShippingAddressMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserService userService;
    @Resource
    private UserShippingAddressService userShippingAddressService;
    @Resource
    private UserCouponService userCouponService;
    @Resource
    private CartService cartService;
    @Resource
    private ProductService productService;
    @Resource
    private SkuStockService skuStockService;
    @Resource
    private RabbitMQService rabbitMQService;
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
            redisUtil.setObject(redisKeyOrderToken, orderToken, Long.valueOf(ORDER_TIMEOUT));
            redisUtil.setObject(redisKeyOrderConfirm, orderConfirmVO, Long.valueOf(ORDER_TIMEOUT));
        });
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
        OrderConfirmVO orderConfirmVO = (OrderConfirmVO) redisUtil.getObject(redisKeyOrderConfirm);

        if (ObjectUtils.isNotEmpty(form.getCouponsId())) {
            try {
                UserCouponVO userCouponVO = userCouponService.getUserCouponVO(form.getCouponsId());

                if (Constants.COUPON_USED.equals(userCouponVO.getStatus())
                        || Constants.COUPON_INVALID.equals(userCouponVO.getCoupon().getStatus())) {
                    throw new ServiceException("优惠券已失效");
                }

                if (orderConfirmVO.getAmount().compareTo(userCouponVO.getCoupon().getCondition()) > -1) {
                    orderConfirmVO.setCouponAmount(userCouponVO.getCoupon().getDenomination());
                    orderConfirmVO.setActualAmount(orderConfirmVO.getAmount().subtract(userCouponVO.getCoupon().getDenomination()));
                } else {
                    throw new ServiceException("订单未满足优惠条件");
                }
            } catch (Exception e) {
                redisUtil.setObject(redisKeyOrderToken, form.getOrderToken(), Long.valueOf(ORDER_TIMEOUT));
                redisUtil.setObject(redisKeyOrderConfirm, orderConfirmVO, Long.valueOf(ORDER_TIMEOUT));
                throw e;
            }
        } else {
            orderConfirmVO.setCouponAmount(BigDecimal.ZERO);
            orderConfirmVO.setActualAmount(orderConfirmVO.getAmount());
        }

        executor.execute(() -> {
            redisUtil.setObject(redisKeyOrderToken, form.getOrderToken(), Long.valueOf(ORDER_TIMEOUT));
            redisUtil.setObject(redisKeyOrderConfirm, orderConfirmVO, Long.valueOf(ORDER_TIMEOUT));
        });
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
        List<OrderItem> orderItemList = new ArrayList<>();                          // 订单项
        List<SkuStockDTO> skuStockDTOList = new ArrayList<>();                      // 锁定商品库存列表
        /* 缓存获取预生成的订单信息 */
        String redisKeyOrderConfirm = String.format(Constants.ORDER_CONFIRM_REDIS_KEY, userInfoVO.getUserId());
        OrderConfirmVO orderConfirmVO = (OrderConfirmVO) redisUtil.getObject(redisKeyOrderConfirm);
        List<OrderItemVO> orderItemVOList = orderConfirmVO.getOrderItemList();      // 获取预生成的订单项信息
        Set<Integer> productIdSet = orderItemVOList.stream().map(OrderItemVO::getProductId).collect(Collectors.toSet());
        Set<Integer> skuIdSet = orderItemVOList.stream().map(OrderItemVO::getSkuId).collect(Collectors.toSet());
        /* List to Map */
        Map<Integer, ProductVO> productVOMap = productService.getProductVOList(productIdSet).stream()
                .collect(Collectors.toMap(ProductVO::getId, v -> v));                       // 购物车选中的商品
        Map<Integer, Sku> skuMap = productService.getSkuList(skuIdSet).stream()
                .collect(Collectors.toMap(Sku::getId, v -> v));                             // 商品规格
        Map<Integer, SkuStock> stockMap = skuStockService.getSkuStockList(skuIdSet).stream()
                .collect(Collectors.toMap(SkuStock::getSkuId, v -> v));                     // 商品规格库存
        /* 验证库存是否充足 */
        for (OrderItemVO orderItemVO : orderItemVOList) {
            ProductVO productVO = productVOMap.get(orderItemVO.getProductId());

            if (ObjectUtils.isEmpty(productVO)) {
                throw new ServiceException("商品ID:" + orderItemVO.getProductId() + ",商品:" + orderItemVO.getSkuTitle() + ",下架或删除");
            }

            Sku sku = skuMap.get(orderItemVO.getSkuId());
            SkuStock stock = stockMap.get(orderItemVO.getSkuId());

            if (ObjectUtils.isEmpty(sku) || ObjectUtils.isEmpty(stock)) {
                throw new ServiceException("商品规格ID:" + orderItemVO.getSkuId() + ",商品:" + orderItemVO.getSkuTitle() + ",下架或删除");
            }

            if (orderItemVO.getNum() > stock.getStock()) {
                throw new ServiceException("商品规格ID:" + orderItemVO.getSkuId() + ",商品:" + orderItemVO.getSkuTitle() + ",库存不足");
            }

            orderItemList.add(orderItemVO2OrderItem(orderNo, orderItemVO));
            SkuStockDTO skuStockDTO = getSkuStockDTO(stock, orderItemVO);
            skuStockDTOList.add(skuStockDTO);
        }
        /* 订单收货地址信息 */
        UserShippingAddressVO userShippingAddressVO = userShippingAddressService.getUserShippingAddressVO(form.getShippingAddressId());
        OrderShippingAddress orderShippingAddress = userShippingAddressVO2OrderShippingAddress(orderNo, userShippingAddressVO);
        OrderService orderServiceProxy = (OrderService) AopContext.currentProxy();      // 获取接口代理,解决本类方法调用事务失效问题
        orderServiceProxy.insertOrder(orderNo, orderItemList, orderShippingAddress, skuStockDTOList, orderConfirmVO, form, userInfoVO);
        executor.execute(() -> {
            redisUtil.deleteObject(redisKeyOrderConfirm);
            cartService.deleteCartProductVOListBySelected();
        });
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

        List<OrderItem> orderItemList = orderItemMapper.selectListByOrderNo(orderNo);
        OrderShippingAddress orderShippingAddress = orderShippingAddressMapper.selectByPrimaryKey(orderNo);
        OrderVO orderVO = new OrderVO();
        OrderShippingAddressVO orderShippingAddressVO = new OrderShippingAddressVO();
        BeanUtils.copyProperties(order, orderVO);
        BeanUtils.copyProperties(orderShippingAddress, orderShippingAddressVO);
        List<OrderItemVO> orderItemVOList = orderItemList.stream().map(this::orderItem2OrderItemVO).collect(Collectors.toList());
        orderVO.setOrderItemList(orderItemVOList);
        orderVO.setShippingAddress(orderShippingAddressVO);
        return orderVO;
    }

    @Override
    public PageUtil<OrderVO> getOrderVOList(Integer status, SortEnum sortEnum, Integer pageNum, Integer pageSize) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        PageUtil<OrderVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        long total = 0L;
        List<OrderVO> orderVOList = new ArrayList<>();
        List<Order> orderList = orderMapper.selectListByParam(null, null, userInfoVO.getUserId(), null,
                status, Constants.IS_NOT_DELETE, sortEnum.getSortType(), pageNum, pageSize);

        if (CollectionUtils.isEmpty(orderList)) {
            pageUtil.setTotal(total);
            pageUtil.setList(orderVOList);
            return pageUtil;
        }

        total = orderMapper.countByParam(null, null, userInfoVO.getUserId(),
                null, status, Constants.IS_NOT_DELETE);
        Set<Long> orderNoSet = orderList.stream().map(Order::getOrderNo).collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemMapper.selectListByOrderNoSet(orderNoSet);
        Map<Long, List<OrderItemVO>> orderItemVOMap = orderItemList.stream()
                .map(this::orderItem2OrderItemVO).collect(Collectors.groupingBy(OrderItemVO::getOrderNo));
        orderList.stream().map(this::order2OrderVO).collect(Collectors.toList()).forEach(orderVO -> {
            orderVO.setOrderItemList(orderItemVOMap.get(orderVO.getOrderNo()));
            orderVOList.add(orderVO);
        });
        pageUtil.setTotal(total);
        pageUtil.setList(orderVOList);
        return pageUtil;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOrder(Long orderNo, List<OrderItem> orderItemList, OrderShippingAddress orderShippingAddress,
                            List<SkuStockDTO> skuStockDTOList, OrderConfirmVO orderConfirmVO, OrderForm form, UserInfoVO userInfoVO) {
        skuStockService.lockSkuStock(orderNo, skuStockDTOList);     // 锁定库存(此处 SQL 乐观锁处理)
        Order order;                                                // 生成订单信息
        BigDecimal amount = orderConfirmVO.getAmount();             // 订单金额
        /* 是否使用优惠券、验证优惠券有效性 */
        if (ObjectUtils.isNotEmpty(form.getCouponsId())) {
            UserCouponVO userCouponVO = userCouponService.getUserCouponVO(form.getCouponsId());

            if (Constants.COUPON_USED.equals(userCouponVO.getStatus())
                    || Constants.COUPON_INVALID.equals(userCouponVO.getCoupon().getStatus())) {
                throw new ServiceException("优惠券已失效");
            }

            if (ObjectUtils.isNotEmpty(userCouponVO) && amount.compareTo(userCouponVO.getCoupon().getCondition()) > -1) {
                userCouponService.deductUserCoupon(orderNo, userCouponVO.getCouponId());        // 扣除用户优惠券
                order = buildOrder(orderNo, userInfoVO.getUserId(), userCouponVO.getCouponId(), orderConfirmVO.getPostage(),
                        amount, userCouponVO.getCoupon().getDenomination(), orderConfirmVO.getDiscountAmount());
            } else {
                throw new ServiceException("订单未满足优惠条件");
            }
        } else {
            order = buildOrder(orderNo, userInfoVO.getUserId(), form.getCouponsId(), orderConfirmVO.getPostage(),
                    amount, BigDecimal.ZERO, BigDecimal.ZERO);
        }
        /* 保存订单信息 */
        insertOrder(orderNo, order);
        batchInsertOrderItem(orderNo, orderItemList);
        insertOrderShippingAddress(orderNo, orderShippingAddress);
        /* 发送延迟消息,15分钟订单未支付自动关闭 */
        rabbitMQService.send(QueueEnum.ORDER_CLOSE_QUEUE.getExchange(), QueueEnum.ORDER_CLOSE_QUEUE.getRoutingKey(), order, ORDER_TIMEOUT);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sign4Order(Long orderNo) {
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
    public void cancelOrder(Long orderNo) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Order order = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(orderNo, userInfoVO.getUserId(),
                OrderStatusEnum.NOT_PAY.getCode(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isEmpty(order)) {
            throw new ServiceException("订单号:" + orderNo + ",无法取消该订单");
        }

        skuStockService.unLockSkuStock(orderNo, OrderStatusEnum.NOT_PAY);

        if (ObjectUtils.isNotEmpty(order.getCouponId())) {
            userCouponService.rollbackUserCoupon(orderNo, order.getCouponId());
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
    public void handleTimeoutOrder(Order order) {
        Order timeoutOrder = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(order.getOrderNo(), null,
                OrderStatusEnum.NOT_PAY.getCode(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isNotEmpty(timeoutOrder) && ObjectUtils.isEmpty(timeoutOrder.getConsumeVersion())) {
            skuStockService.unLockSkuStock(order.getOrderNo(), OrderStatusEnum.TIMEOUT);

            if (ObjectUtils.isNotEmpty(timeoutOrder.getCouponId())) {
                userCouponService.rollbackUserCoupon(order.getOrderNo(), order.getCouponId());
            }

            timeoutOrder.setStatus(OrderStatusEnum.TIMEOUT.getCode());
            timeoutOrder.setDeleteStatus(Constants.IS_DELETE);
            timeoutOrder.setConsumeVersion(1);
            int row = orderMapper.updateByPrimaryKeySelective(timeoutOrder);

            if (row <= 0) {
                throw new ServiceException("订单号:" + timeoutOrder.getOrderNo() + ",订单关闭失败");
            }

            log.info("订单号:'{}',订单关闭成功.", timeoutOrder.getOrderNo());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlePaySuccessOrder(PayInfo payInfo) {
        Order paySuccessOrder = orderMapper.selectByOrderNoOrUserIdOrStatusOrDeleteStatus(payInfo.getOrderNo(), null,
                OrderStatusEnum.NOT_PAY.getCode(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isNotEmpty(paySuccessOrder) && ObjectUtils.isEmpty(paySuccessOrder.getConsumeVersion())) {
            skuStockService.unLockSkuStock(payInfo.getOrderNo(), OrderStatusEnum.PAY_SUCCESS);
            paySuccessOrder.setStatus(OrderStatusEnum.PAY_SUCCESS.getCode());
            paySuccessOrder.setConsumeVersion(1);
            paySuccessOrder.setPaymentTime(new Date());
            int row = orderMapper.updateByPrimaryKeySelective(paySuccessOrder);

            if (row <= 0) {
                throw new ServiceException("订单号:" + paySuccessOrder.getOrderNo() + ",订单确认支付失败");
            }

            log.info("订单号:'{}',订单确认支付成功.", paySuccessOrder.getOrderNo());
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

    private OrderItem orderItemVO2OrderItem(Long orderNo, OrderItemVO orderItemVO) {
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(orderItemVO, orderItem);
        orderItem.setOrderNo(orderNo);
        return orderItem;
    }

    private OrderShippingAddress userShippingAddressVO2OrderShippingAddress(Long orderNo, UserShippingAddressVO userShippingAddressVO) {
        OrderShippingAddress orderShippingAddress = new OrderShippingAddress();
        BeanUtils.copyProperties(userShippingAddressVO, orderShippingAddress);
        orderShippingAddress.setOrderNo(orderNo);
        return orderShippingAddress;
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
                    .filter(e -> amount.compareTo(e.getCoupon().getCondition()) > -1).collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(userCouponVOS)) {
                orderConfirmVO.setCouponList(userCouponVOList); // 满足优惠条件的优惠券列表
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

    private OrderVO order2OrderVO(Order order) {
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);
        return orderVO;
    }

    private OrderItemVO cartProductVO2OrderItemVO(CartProductVO cartProductVO) {
        OrderItemVO orderItemVO = new OrderItemVO();
        orderItemVO.setSkuId(cartProductVO.getSkuId());
        orderItemVO.setProductId(cartProductVO.getProductId());
        orderItemVO.setSkuTitle(cartProductVO.getSkuTitle());
        orderItemVO.setSkuParam(cartProductVO.getParam());
        orderItemVO.setSkuPicture(cartProductVO.getPicture());
        orderItemVO.setNum(cartProductVO.getQuantity());
        orderItemVO.setPrice(cartProductVO.getPrice());
        orderItemVO.setTotalPrice(cartProductVO.getTotalPrice());
        return orderItemVO;
    }

    private OrderItemVO orderItem2OrderItemVO(OrderItem orderItem) {
        OrderItemVO orderItemVO = new OrderItemVO();
        BeanUtils.copyProperties(orderItem, orderItemVO);
        return orderItemVO;
    }

    private SkuStockDTO getSkuStockDTO(SkuStock stock, OrderItemVO orderItemVO) {
        SkuStockDTO skuStockDTO = new SkuStockDTO();
        skuStockDTO.setSkuId(orderItemVO.getSkuId());
        skuStockDTO.setProductId(orderItemVO.getProductId());
        skuStockDTO.setSkuStockId(stock.getId());
        skuStockDTO.setLockStock(orderItemVO.getNum());
        skuStockDTO.setStock(orderItemVO.getNum());
        skuStockDTO.setQuantity(orderItemVO.getNum());
        return skuStockDTO;
    }

    private void insertOrder(Long orderNo, Order order) {
        int row = orderMapper.insertSelective(order);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",订单信息保存失败");
        }
    }

    private void batchInsertOrderItem(Long orderNo, List<OrderItem> orderItemList) {
        int row = orderItemMapper.batchInsertByOrderItemList(orderItemList);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",订单项保存失败");
        }
    }

    private void insertOrderShippingAddress(Long orderNo, OrderShippingAddress orderShippingAddress) {
        int row = orderShippingAddressMapper.insertSelective(orderShippingAddress);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",订单收货地址保存失败");
        }
    }

}