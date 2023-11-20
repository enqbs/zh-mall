package com.enqbs.admin.controller;

import com.enqbs.admin.form.CouponForm;
import com.enqbs.admin.service.coupon.CouponService;
import com.enqbs.admin.vo.CouponVO;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import java.util.Date;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Resource
    private CouponService couponService;

    @GetMapping("/list")
    public R<PageUtil<CouponVO>> couponList(@RequestParam(required = false) Integer productId,
                                            @RequestParam(required = false) Date startDate,
                                            @RequestParam(required = false) Date endDate,
                                            @RequestParam(required = false) Integer status,
                                            @RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                            @RequestParam(required = false, defaultValue = "DESC") SortEnum sortEnum,
                                            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        if (pageNum <= 0) {
            pageNum = 1;
        }

        if (pageSize <= 0) {
            pageSize = 10;
        }

        PageUtil<CouponVO> pageCouponList = couponService.getCouponVOList(productId, startDate, endDate, status,
                deleteStatus, sortEnum, pageNum, pageSize);
        return R.ok(pageCouponList);
    }

    @GetMapping("/{couponId}")
    public R<CouponVO> couponDetail(@PathVariable Integer couponId) {
        CouponVO couponInfo = couponService.getCouponVO(couponId);
        return R.ok(couponInfo);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('COUPON:ADD')")
    public R<Void> addCoupon(@Valid @RequestBody CouponForm form) {
        int row = couponService.insert(form);

        if (row <= 0) {
            throw new ServiceException("优惠券保存失败");
        }

        return R.ok("优惠券保存成功");
    }

    @PutMapping("/{couponId}")
    @PreAuthorize("hasAuthority('COUPON:UPDATE')")
    public R<Void> updateCoupon(@PathVariable Integer couponId, @Valid @RequestBody CouponForm form) {
        int row = couponService.update(couponId, form);

        if (row <= 0) {
            throw new ServiceException("优惠券更新失败");
        }

        return R.ok("优惠券更新成功");
    }

    @DeleteMapping("/{couponId}")
    @PreAuthorize("hasAuthority('COUPON:DELETE')")
    public R<Void> deleteCoupon(@PathVariable Integer couponId) {
        int row = couponService.delete(couponId);

        if (row <= 0) {
            throw new ServiceException("优惠券删除失败");
        }

        return R.ok("优惠券删除成功");
    }

}
