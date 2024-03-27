package com.enqbs.admin.controller;

import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.form.LogisticsInfoForm;
import com.enqbs.admin.service.order.OrderLogisticsInfoService;
import com.enqbs.admin.service.order.OrderService;
import com.enqbs.admin.vo.OrderVO;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderLogisticsInfoService orderLogisticsInfoService;

    @GetMapping("/list")
    public R<PageUtil<OrderVO>> orderListPage(@RequestParam(required = false) Long orderNo,
                                              @RequestParam(required = false) String orderSc,
                                              @RequestParam(required = false) Integer userId,
                                              @RequestParam(required = false) Integer paymentType,
                                              @RequestParam(required = false) Integer status,
                                              @RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                              @RequestParam(required = false, defaultValue = "DESC") SortEnum sort,
                                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageUtil<OrderVO> orderVOListPage = orderService.orderVOListPage(orderNo, orderSc, userId, paymentType, status,
                deleteStatus, sort, pageNum <= 0 ? 1 : pageNum, pageSize <= 0 ? 10 : pageSize);
        return R.ok(orderVOListPage);
    }

    @GetMapping("/{orderNo}")
    public R<OrderVO> orderDetail(@PathVariable Long orderNo) {
        OrderVO orderInfo = orderService.getOrderVO(orderNo);
        return R.ok(orderInfo);
    }

    @PostMapping("/shipment/{orderNo}")
    @PreAuthorize("hasAuthority('ORDER:UPDATE')")
    public R<Map<String, Long>> orderShipment(@PathVariable Long orderNo, @Valid @RequestBody LogisticsInfoForm form) {
        orderService.shipment(orderNo, form);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("orderNo", orderNo);
        return R.ok("发货成功", resultMap);
    }

    @PutMapping("/logistics-info/{orderNo}")
    @PreAuthorize("hasAuthority('ORDER:UPDATE')")
    public R<Map<String, Long>> updateOrderLogisticsInfo(@PathVariable Long orderNo, @Valid @RequestBody LogisticsInfoForm form) {
        int row = orderLogisticsInfoService.update(orderNo, form);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",修改订单快递信息失败");
        }

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("orderNo", orderNo);
        return R.ok("修改订单快递信息成功", resultMap);
    }

}
