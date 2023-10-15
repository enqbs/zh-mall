package com.enqbs.app.service.impl;

import com.enqbs.app.service.CartService;
import com.enqbs.app.service.OrderService;
import com.enqbs.app.service.RabbitMQService;
import com.enqbs.app.service.UserService;
import com.enqbs.app.service.UserShippingAddressService;
import com.enqbs.app.vo.CartProductVO;
import com.enqbs.app.vo.OrderConfirmVO;
import com.enqbs.app.vo.OrderItemVO;
import com.enqbs.app.vo.UserInfoVO;
import com.enqbs.app.vo.UserShippingAddressVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.util.IDUtil;
import com.enqbs.common.util.RedisUtil;
import com.enqbs.generator.dao.OrderItemMapper;
import com.enqbs.generator.dao.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private UserService userService;
    @Resource
    private UserShippingAddressService userShippingAddressService;
    @Resource
    private CartService cartService;
    @Resource
    private RabbitMQService rabbitMQService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ThreadPoolExecutor executor;

    @Override
    public OrderConfirmVO getOrderConfirmVO() {
        OrderConfirmVO orderConfirmVO = new OrderConfirmVO();
        long orderTokenTimeout = 900000L;           // orderToken 超时时间
        long orderConfirmVOTimeout = 1800000L;      // 订单确认信息缓存超时时间
        String orderToken = IDUtil.getUUID();

        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();     // 登录凭证

        CompletableFuture<Void> shippingAddressFuture = CompletableFuture.runAsync(() -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            List<UserShippingAddressVO> shippingAddressVOList = userShippingAddressService.getUserShippingAddressVOList();
            orderConfirmVO.setShippingAddressVOList(shippingAddressVOList);
        }, executor);

        CompletableFuture<Void> orderItemFuture = CompletableFuture.runAsync(() -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            List<CartProductVO> cartProductVOList = cartService.getCartProductVOListBySelected();       // 购物车选中的商品
            List<OrderItemVO> orderItemVOList = new ArrayList<>();

            for (CartProductVO cartProductVO : cartProductVOList) {
                OrderItemVO orderItemVO = new OrderItemVO();
                orderItemVO.setSkuId(cartProductVO.getSkuId());
                orderItemVO.setProductId(cartProductVO.getProductId());
                orderItemVO.setSkuTitle(cartProductVO.getSkuTitle());
                orderItemVO.setSkuParam(cartProductVO.getParam());
                orderItemVO.setSkuPicture(cartProductVO.getPicture());
                orderItemVO.setNum(cartProductVO.getQuantity());
                orderItemVO.setPrice(cartProductVO.getPrice());
                orderItemVO.setTotalPrice(cartProductVO.getTotalPrice());
                orderItemVOList.add(orderItemVO);
            }
            orderConfirmVO.setOrderItemVOList(orderItemVOList);
            BigDecimal amount = orderItemVOList.stream().map(OrderItemVO::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            orderConfirmVO.setAmount(amount);
        }, executor);

        CompletableFuture<Void> orderTokenFuture = CompletableFuture.runAsync(() -> {
            orderConfirmVO.setOrderToken(orderToken);
            String redisKey = String.format(Constants.ORDER_TOKEN_REDIS_KEY, userInfoVO.getUserId());
            redisUtil.setObject(redisKey, orderToken, orderTokenTimeout);
        }, executor);
        CompletableFuture.allOf(shippingAddressFuture, orderItemFuture, orderTokenFuture).join();
        redisUtil.setObject(orderToken, orderConfirmVO, orderConfirmVOTimeout);
        return orderConfirmVO;
    }

}
