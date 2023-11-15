package com.enqbs.app.service.impl;

import com.enqbs.app.form.OrderForm;
import com.enqbs.app.pojo.dto.SkuStockDTO;
import com.enqbs.app.pojo.vo.OrderShippingAddressVO;
import com.enqbs.app.service.CartService;
import com.enqbs.app.service.OrderService;
import com.enqbs.app.service.ProductService;
import com.enqbs.app.service.RabbitMQService;
import com.enqbs.app.service.SkuStockService;
import com.enqbs.app.service.UserService;
import com.enqbs.app.service.UserShippingAddressService;
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
import org.springframework.beans.BeanUtils;
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
    private UserShippingAddressService shippingAddressService;
    @Resource
    private CartService cartService;
    @Resource
    private ProductService productService;
    @Resource
    private SkuStockService skuStockService;
    @Resource
    private RabbitMQService rabbitMQService;

    private static final Integer ORDER_TIMEOUT = 900000;

    @Override
    public OrderConfirmVO getOrderConfirmVO() {
        OrderConfirmVO orderConfirmVO = new OrderConfirmVO();
        String orderToken = IDUtil.getUUID();

        UserInfoVO userInfoVO = userService.getUserInfoVO();
        List<CartProductVO> cartProductVOList = cartService.getCartProductVOListBySelected();       // 购物车选中的商品

        if (CollectionUtils.isEmpty(cartProductVOList)) {
            throw new ServiceException("请选中商品再下单");
        }

        List<OrderItemVO> orderItemVOList = new ArrayList<>();

        for (CartProductVO cartProductVO : cartProductVOList) {
            OrderItemVO orderItemVO = cartProductVO2OrderItemVO(cartProductVO);
            orderItemVOList.add(orderItemVO);
        }

        List<UserShippingAddressVO> shippingAddressVOList = shippingAddressService.getUserShippingAddressVOList();
        orderConfirmVO.setShippingAddressList(shippingAddressVOList);
        orderConfirmVO.setOrderItemList(orderItemVOList);
        BigDecimal amount = orderItemVOList.stream().map(OrderItemVO::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderConfirmVO.setAmount(amount);
        orderConfirmVO.setOrderToken(orderToken);
        String redisKey = String.format(Constants.ORDER_TOKEN_REDIS_KEY, userInfoVO.getUserId());
        redisUtil.setObject(redisKey, orderToken, Long.valueOf(ORDER_TIMEOUT));
        return orderConfirmVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long insertOrder(OrderForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        String redisKey = String.format(Constants.ORDER_TOKEN_REDIS_KEY, userInfoVO.getUserId());
        Long result = redisUtil.executeScript(Constants.REDIS_SCRIPT, redisKey, form.getOrderToken());

        if (result == 0) {
            throw new ServiceException("订单凭证失效,请刷新页面");
        }

        long orderNo = IDUtil.getId();
        Order order = new Order();
        List<OrderItem> orderItemList = new ArrayList<>();                          // 订单项
        OrderShippingAddress orderShippingAddress = new OrderShippingAddress();     // 订单收货地址
        List<SkuStockDTO> skuStockDTOList = new ArrayList<>();                      // 锁定商品库存列表
        List<CartProductVO> cartProductVOList = cartService.getCartProductVOListBySelected();

        if (CollectionUtils.isEmpty(cartProductVOList)) {
            throw new ServiceException("请选中商品再下单");
        }

        Set<Integer> productIdSet = cartProductVOList.stream().map(CartProductVO::getProductId).collect(Collectors.toSet());
        Set<Integer> skuIdSet = cartProductVOList.stream().map(CartProductVO::getSkuId).collect(Collectors.toSet());
        /* List to Map */
        Map<Integer, ProductVO> productVOMap = productService.getProductVOList(productIdSet).stream()
                .collect(Collectors.toMap(ProductVO::getId, v -> v));                       // 购物车选中的商品
        Map<Integer, Sku> skuMap = productService.getSkuList(skuIdSet).stream()
                .collect(Collectors.toMap(Sku::getId, v -> v));                             // 商品规格
        Map<Integer, SkuStock> stockMap = skuStockService.getSkuStockList(skuIdSet).stream()
                .collect(Collectors.toMap(SkuStock::getSkuId, v -> v));                     // 商品规格库存

        for (CartProductVO cartProductVO : cartProductVOList) {
            ProductVO productVO = productVOMap.get(cartProductVO.getProductId());

            if (ObjectUtils.isEmpty(productVO)) {
                throw new ServiceException("商品ID:" + cartProductVO.getProductId() +
                        ",商品:" + cartProductVO.getProductTitle() + ",下架或删除");
            }

            Sku sku = skuMap.get(cartProductVO.getSkuId());
            SkuStock stock = stockMap.get(cartProductVO.getSkuId());

            if (ObjectUtils.isEmpty(sku) || ObjectUtils.isEmpty(stock)) {
                throw new ServiceException("商品规格ID:" + cartProductVO.getSkuId() +
                        ",商品规格:" + cartProductVO.getSkuTitle() + ",下架或删除");
            }

            if (cartProductVO.getQuantity() > stock.getStock()) {
                throw new ServiceException("商品规格ID:" + cartProductVO.getSkuId() +
                        ",商品规格:" + cartProductVO.getSkuTitle() + ",库存不足");
            }

            OrderItem orderItem = new OrderItem();
            OrderItemVO orderItemVO = cartProductVO2OrderItemVO(cartProductVO);
            BeanUtils.copyProperties(orderItemVO, orderItem);
            orderItem.setOrderNo(orderNo);
            orderItemList.add(orderItem);
            SkuStockDTO skuStockDTO = getSkuStockDTO(stock, cartProductVO);
            skuStockDTOList.add(skuStockDTO);
        }

        skuStockService.lockSkuStock(orderNo, skuStockDTOList);      // 锁定库存
        UserShippingAddressVO shippingAddressVO = shippingAddressService.getUserShippingAddressVO(form.getShippingAddressId());
        BeanUtils.copyProperties(shippingAddressVO, orderShippingAddress);
        orderShippingAddress.setOrderNo(orderNo);
        BigDecimal amount = orderItemList.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setOrderNo(orderNo);
        order.setUserId(userInfoVO.getUserId());
        order.setAmount(amount);
        /* 保存订单信息 */
        insertOrder(orderNo, order);
        batchInsertOrderItem(orderNo, orderItemList);
        insertOrderShippingAddress(orderNo, orderShippingAddress);
        /* 发送延迟消息。15分钟订单超时 */
        rabbitMQService.send(QueueEnum.ORDER_CLOSE_QUEUE.getExchange(), QueueEnum.ORDER_CLOSE_QUEUE.getRoutingKey(), order, ORDER_TIMEOUT);
        cartService.deleteCartProductVOListBySelected();
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

    private SkuStockDTO getSkuStockDTO(SkuStock stock, CartProductVO cartProductVO) {
        SkuStockDTO skuStockDTO = new SkuStockDTO();
        skuStockDTO.setSkuId(cartProductVO.getSkuId());
        skuStockDTO.setProductId(cartProductVO.getProductId());
        skuStockDTO.setSkuStockId(stock.getId());
        skuStockDTO.setLockStock(cartProductVO.getQuantity());
        skuStockDTO.setStock(cartProductVO.getQuantity());
        skuStockDTO.setQuantity(cartProductVO.getQuantity());
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
