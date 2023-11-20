package com.enqbs.app.controller;

import com.enqbs.app.form.OrderConfirmForm;
import com.enqbs.app.form.OrderForm;
import com.enqbs.app.service.order.OrderService;
import com.enqbs.app.pojo.vo.OrderConfirmVO;
import com.enqbs.app.pojo.vo.OrderVO;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/confirm")
    public R<OrderConfirmVO> orderConfirmationPage() {
        OrderConfirmVO orderConfirmInfo = orderService.getOrderConfirmVO();
        return R.ok(orderConfirmInfo);
    }

    @GetMapping("/coupon")
    public R<OrderConfirmVO> orderConfirmationPage(@Valid @RequestBody OrderConfirmForm form) {
        OrderConfirmVO orderConfirmInfo = orderService.getOrderConfirmVO(form);
        return R.ok(orderConfirmInfo);
    }

    @PostMapping
    public R<Map<String, Long>> submitOrder(@Valid @RequestBody OrderForm form) {
        Long orderNo = orderService.submit(form);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("orderNo", orderNo);
        return R.ok("订单提交成功", resultMap);
    }

    @GetMapping("/{orderNo}")
    public R<OrderVO> orderDetail(@PathVariable Long orderNo) {
        OrderVO orderInfo = orderService.getOrderVO(orderNo);
        return R.ok(orderInfo);
    }

    @GetMapping("/list")
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

    @PutMapping("/receipt/{orderNo}")
    public R<Map<String, Long>> receiptOrder(@PathVariable Long orderNo) {
        orderService.sign(orderNo);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("orderNo", orderNo);
        return R.ok("订单签收成功", resultMap);
    }

    @PutMapping("/cancel/{orderNo}")
    public R<Map<String, Long>> cancelOrder(@PathVariable Long orderNo) {
        orderService.cancel(orderNo);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("orderNo", orderNo);
        return R.ok("订单取消成功", resultMap);
    }

}
