package com.enqbs.app.controller;

import com.enqbs.app.form.OrderForm;
import com.enqbs.app.service.OrderService;
import com.enqbs.app.pojo.vo.OrderConfirmVO;
import com.enqbs.app.pojo.vo.OrderVO;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/order/confirm")
    public R<OrderConfirmVO> orderConfirmationPage() {
        OrderConfirmVO orderConfirmVO = orderService.getOrderConfirmVO();
        return R.ok(orderConfirmVO);
    }

    @PostMapping("/order")
    public R<OrderVO> orderSubmit(@Valid @RequestBody OrderForm form) {
        OrderVO orderVO = orderService.insertOrder(form);
        return R.ok(orderVO);
    }

    @GetMapping("/order/{orderNo}")
    public R<OrderVO> orderDetail(@PathVariable String orderNo) {
        OrderVO orderVO = orderService.getOrderVO(Long.valueOf(orderNo));
        return R.ok(orderVO);
    }

    @GetMapping("/order/list")
    public R<PageUtil<OrderVO>> orderList(@RequestParam(required = false) Integer status,
                                          @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        if (pageNum <= 0) {
            pageNum = 1;
        }

        if (pageSize <= 0) {
            pageSize = 5;
        }
        PageUtil<OrderVO> pageOrderVOList = orderService.getOrderVOList(status, pageNum, pageSize);
        return R.ok(pageOrderVOList);
    }

    @PutMapping("/order/{orderNo}")
    public R<Void> orderCancel(@PathVariable String orderNo) {
        int updateRow = orderService.cancelOrder(Long.valueOf(orderNo));

        if (updateRow <= 0) {
            throw new ServiceException("取消订单失败");
        }
        return R.ok("取消订单成功");
    }

}
