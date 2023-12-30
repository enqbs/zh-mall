package com.enqbs.app.controller;

import com.enqbs.app.pojo.vo.CouponVO;
import com.enqbs.app.service.coupon.CouponService;
import com.enqbs.app.enums.SortEnum;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Resource
    private CouponService couponService;

    @GetMapping("/list")
    public R<PageUtil<CouponVO>> couponPage(@RequestParam(required = false, defaultValue = "DESC") SortEnum sort,
                                            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                            @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        PageUtil<CouponVO> couponVOPage = couponService.couponVOPage(sort, pageNum <= 0 ? 1 : pageNum, pageSize <= 0 ? 5 : pageSize);
        return R.ok(couponVOPage);
    }

    @GetMapping("/{couponId}")
    public R<CouponVO> couponDetail(@PathVariable Integer couponId) {
        CouponVO couponInfo = couponService.getCouponVO(couponId);
        return R.ok(couponInfo);
    }

}
