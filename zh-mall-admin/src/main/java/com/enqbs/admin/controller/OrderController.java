package com.enqbs.admin.controller;

import com.enqbs.admin.form.LogisticsInfoForm;
import com.enqbs.admin.service.order.OrderService;
import com.enqbs.admin.vo.OrderVO;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/order/list")
    public R<PageUtil<OrderVO>> orderList(@RequestParam(required = false) Long orderNo,
                                          @RequestParam(required = false) String orderSc,
                                          @RequestParam(required = false) Integer userId,
                                          @RequestParam(required = false) Integer paymentType,
                                          @RequestParam(required = false) Integer status,
                                          @RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                          @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        if (pageNum <= 0) {
            pageNum = 1;
        }

        if (pageSize <= 0) {
            pageSize = 10;
        }

        PageUtil<OrderVO> pageOrderVOList = orderService.getOrderVOList(orderNo, orderSc, userId, paymentType, status, deleteStatus, pageNum, pageSize);
        return R.ok(pageOrderVOList);
    }

    @GetMapping("/order/{orderNo}")
    public R<OrderVO> orderDetail(@PathVariable Long orderNo) {
        OrderVO orderVO = orderService.getOrderVO(orderNo);
        return R.ok(orderVO);
    }

    @PostMapping("/order/shipment/{orderNo}")
    @PreAuthorize("hasAuthority('ORDER:UPDATE')")
    public R<Void> orderShipment(@PathVariable Long orderNo, @Valid @RequestBody LogisticsInfoForm form) {
        int row = orderService.insertOrderLogisticsInfo(orderNo, form);

        if (row <= 0) {
            throw new ServiceException("发货失败");
        }

        return R.ok("发货成功");
    }

    @PutMapping("/order/logistics-info/{orderNo}")
    @PreAuthorize("hasAuthority('ORDER:UPDATE')")
    public R<Void> updateOrderLogisticsInfo(@PathVariable Long orderNo, @Valid @RequestBody LogisticsInfoForm form) {
        int row = orderService.updateOrderLogisticsInfo(orderNo, form);

        if (row <= 0) {
            throw new ServiceException("修改失败");
        }

        return R.ok("修改成功");
    }

}
