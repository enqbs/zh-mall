package com.enqbs.app.controller;

import com.enqbs.app.service.coupon.CouponService;
import com.enqbs.common.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class CouponController {

    @Resource
    private CouponService couponService;

}
