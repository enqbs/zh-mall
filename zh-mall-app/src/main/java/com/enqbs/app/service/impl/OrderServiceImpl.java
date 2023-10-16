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
import com.enqbs.generator.pojo.Sku;
import com.enqbs.generator.pojo.SkuStock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private RedissonClient redissonClient;
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
            OrderItemVO orderItemVO = getOrderItemVO(cartProductVO);
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
    public OrderVO insertOrder(OrderForm form) {
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
        List<ProductVO> productVOList = productService.getProductVOList(productIdSet);      // 购物车选中的商品
        List<Sku> skuList = productService.getSkuList(skuIdSet);                            // 商品规格
        List<SkuStock> stockList = skuStockService.getSkuStockList(skuIdSet);               // 商品规格库存
        /* List to Map */
        Map<Integer, ProductVO> productVOMap = productVOList.stream().collect(Collectors.toMap(ProductVO::getId, productVO -> productVO));
        Map<Integer, Sku> skuMap = skuList.stream().collect(Collectors.toMap(Sku::getId, sku -> sku));
        Map<Integer, SkuStock> stockMap = stockList.stream().collect(Collectors.toMap(SkuStock::getSkuId, skuStock -> skuStock));

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

            if (stock.getStock() < cartProductVO.getQuantity()) {
                throw new ServiceException("商品规格ID:" + cartProductVO.getSkuId() +
                        ",商品规格:" + cartProductVO.getSkuTitle() + ",库存不足");
            }
            OrderItem orderItem = new OrderItem();
            OrderItemVO orderItemVO = getOrderItemVO(cartProductVO);
            BeanUtils.copyProperties(orderItemVO, orderItem);
            orderItem.setOrderNo(orderNo);
            orderItemList.add(orderItem);
            SkuStockDTO skuStockDTO = getSkuStockDTO(orderNo, stock, cartProductVO);
            skuStockDTOList.add(skuStockDTO);
        }
        UserShippingAddressVO shippingAddressVO = shippingAddressService.getUserShippingAddressVO(form.getShippingAddressId());
        BeanUtils.copyProperties(shippingAddressVO, orderShippingAddress);
        orderShippingAddress.setOrderNo(orderNo);

        BigDecimal amount = orderItemList.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setOrderNo(orderNo);
        order.setUserId(userInfoVO.getUserId());
        order.setAmount(amount);
        /* 保存订单信息 */
        insertOrder(order);
        batchInsertOrderItem(orderItemList);
        insertOrderShippingAddress(orderShippingAddress);
        RLock lock = redissonClient.getLock(Constants.SKU_STOCK_LOCK);
        lock.lock();

        try {
            skuStockService.lockSkuStock(skuStockDTOList);      // 锁定库存
        } finally {
            lock.unlock();
        }
        /* 发送延迟消息。15分钟订单超时 */
        rabbitMQService.send(QueueEnum.ORDER_CLOSE_QUEUE.getExchange(), QueueEnum.ORDER_CLOSE_QUEUE.getRoutingKey(), order, ORDER_TIMEOUT);
        cartService.deleteCartProductVOListBySelected();
        return getOrderVO(orderNo);
    }

    @Override
    public OrderVO getOrderVO(Long orderNo) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Order order = orderMapper.selectByOrderNo(orderNo);

        if (ObjectUtils.isEmpty(order) || !userInfoVO.getUserId().equals(order.getUserId())) {
            throw new ServiceException("订单不存在");
        }
        List<OrderItem> orderItemList = orderItemMapper.selectListByOrderNo(orderNo);
        OrderShippingAddress orderShippingAddress = orderShippingAddressMapper.selectByPrimaryKey(orderNo);
        OrderVO orderVO = new OrderVO();
        OrderShippingAddressVO orderShippingAddressVO = new OrderShippingAddressVO();
        BeanUtils.copyProperties(order, orderVO);
        BeanUtils.copyProperties(orderShippingAddress, orderShippingAddressVO);
        List<OrderItemVO> orderItemVOList = orderItemList.stream().map(this::getOrderItemVO).collect(Collectors.toList());
        orderVO.setOrderItemList(orderItemVOList);
        orderVO.setShippingAddress(orderShippingAddressVO);
        return orderVO;
    }

    @Override
    public PageUtil<OrderVO> getOrderVOList(Integer status, Integer pageNum, Integer pageSize) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        PageUtil<OrderVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        long total = 0L;
        List<Order> orderList = orderMapper.selectListByParam(userInfoVO.getUserId(), status, pageNum, pageSize);
        List<OrderVO> orderVOList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(orderList)) {
            total = orderMapper.countByUserId(userInfoVO.getUserId(), status);
            Set<Long> orderNoSet = orderList.stream().map(Order::getOrderNo).collect(Collectors.toSet());
            List<OrderItem> orderItemList = orderItemMapper.selectListByOrderNoSet(orderNoSet);
            Map<Long, List<OrderItemVO>> orderItemVOMap = orderItemList.stream()
                    .map(this::getOrderItemVO).collect(Collectors.groupingBy(OrderItemVO::getOrderNo));

            for (Order order : orderList) {
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(order, orderVO);
                orderVO.setOrderItemList(orderItemVOMap.get(orderVO.getOrderNo()));
                orderVOList.add(orderVO);
            }
        }
        pageUtil.setTotal(total);
        pageUtil.setList(orderVOList);
        return pageUtil;
    }

    @Override
    public int cancelOrder(Long orderNo) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Order order = orderMapper.selectByOrderNo(orderNo);

        if (ObjectUtils.isEmpty(order) ||
                !OrderStatusEnum.NOT_PAY.getCode().equals(order.getStatus()) ||
                !userInfoVO.getUserId().equals(order.getUserId())) {
            throw new ServiceException("无法取消该订单");
        }
        order.setDeleteStatus(Constants.IS_DELETE);
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void handleTimeoutOrder(Order order) {
        Order timeoutOrder = orderMapper.selectByOrderNo(order.getOrderNo());

        if (ObjectUtils.isNotEmpty(timeoutOrder) &&
                OrderStatusEnum.NOT_PAY.getCode().equals(timeoutOrder.getStatus()) &&
                ObjectUtils.isEmpty(timeoutOrder.getConsumeVersion())) {
            timeoutOrder.setStatus(OrderStatusEnum.TIMEOUT.getCode());
            timeoutOrder.setDeleteStatus(Constants.IS_DELETE);
            timeoutOrder.setConsumeVersion(1);
            orderMapper.updateByPrimaryKeySelective(timeoutOrder);
        }
    }

    private OrderItemVO getOrderItemVO(CartProductVO cartProductVO) {
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

    private OrderItemVO getOrderItemVO(OrderItem orderItem) {
        OrderItemVO orderItemVO = new OrderItemVO();
        BeanUtils.copyProperties(orderItem, orderItemVO);
        return orderItemVO;
    }

    private SkuStockDTO getSkuStockDTO(Long orderNo, SkuStock stock, CartProductVO product) {
        SkuStockDTO skuStockDTO = new SkuStockDTO();
        skuStockDTO.setOrderNo(orderNo);
        skuStockDTO.setSkuId(product.getSkuId());
        skuStockDTO.setProductId(product.getProductId());
        skuStockDTO.setSkuStockId(stock.getId());
        skuStockDTO.setActualStock(stock.getActualStock());
        skuStockDTO.setLockStock(stock.getLockStock() + product.getQuantity());
        skuStockDTO.setStock(stock.getStock() - product.getQuantity());
        skuStockDTO.setQuantity(product.getQuantity());
        return skuStockDTO;
    }

    private void insertOrder(Order order) {
        int insertRow = orderMapper.insertSelective(order);

        if (insertRow <= 0) {
            throw new ServiceException("订单信息保存失败");
        }
        log.info("订单信息保存成功,{}", order);
    }

    private void batchInsertOrderItem(List<OrderItem> orderItemList) {
        int batchInsertRow = orderItemMapper.batchInsertByOrderItemList(orderItemList);

        if (batchInsertRow <= 0) {
            throw new ServiceException("订单项保存失败");
        }
        log.info("订单项保存成功,{}", orderItemList);
    }

    private void insertOrderShippingAddress(OrderShippingAddress orderShippingAddress) {
        int insertRow = orderShippingAddressMapper.insertSelective(orderShippingAddress);

        if (insertRow <= 0) {
            throw new ServiceException("订单收货地址保存失败");
        }
        log.info("订单收货地址保存成功,{}", orderShippingAddress);
    }

}
