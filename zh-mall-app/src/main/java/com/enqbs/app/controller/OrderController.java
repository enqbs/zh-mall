package com.enqbs.app.controller;

import com.enqbs.app.service.OrderService;
import com.enqbs.app.vo.OrderConfirmVO;
import com.enqbs.common.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/order/confirm")
    public R<OrderConfirmVO> orderConfirmationPage() {
        OrderConfirmVO orderConfirmVO = orderService.getOrderConfirmVO();
        return R.ok(orderConfirmVO);
    }

}
