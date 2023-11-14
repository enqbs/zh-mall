package com.enqbs.app.controller;

import com.enqbs.app.form.OrderForm;
import com.enqbs.app.service.OrderService;
import com.enqbs.app.pojo.vo.OrderConfirmVO;
import com.enqbs.app.pojo.vo.OrderVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Slf4j
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/order/confirm")
    public R<OrderConfirmVO> orderConfirmationPage() {
        OrderConfirmVO orderConfirmInfo = orderService.getOrderConfirmVO();
        return R.ok(orderConfirmInfo);
    }

    @PostMapping("/order")
    public R<Long> orderSubmit(@Valid @RequestBody OrderForm form) {
        Long orderNo = orderService.insertOrder(form);
        return R.ok(orderNo);
    }

    @GetMapping("/order/{orderNo}")
    public R<OrderVO> orderDetail(@PathVariable Long orderNo) {
        OrderVO orderInfo = orderService.getOrderVO(orderNo);
        return R.ok(orderInfo);
    }

    @GetMapping("/order/list")
    public R<PageUtil<OrderVO>> orderList(@RequestParam(required = false) Integer status,
                                          @RequestParam(required = false, defaultValue = "DESC") SortEnum sort,
                                          @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        if (pageNum <= 0) {
            pageNum = 1;
        }

        if (pageSize <= 0) {
            pageSize = 5;
        }

        PageUtil<OrderVO> pageOrderList = orderService.getOrderVOList(status, sort, pageNum, pageSize);
        return R.ok(pageOrderList);
    }

    @PutMapping("/order/receipt/{orderNo}")
    public R<Long> orderReceipt(@PathVariable Long orderNo) {
        int row = orderService.sign4Order(orderNo);

        if (row <= 0) {
            throw new ServiceException("订单签收失败,订单号:" + orderNo);
        }

        log.info("订单签收成功,订单号'{}'.", orderNo);
        return R.ok("订单签收成功", orderNo);
    }

    @PutMapping("/order/cancel/{orderNo}")
    public R<Long> orderCancel(@PathVariable Long orderNo) {
        orderService.cancelOrder(orderNo);
        log.info("订单取消成功,订单号'{}'.", orderNo);
        return R.ok("订单取消成功", orderNo);
    }

}
