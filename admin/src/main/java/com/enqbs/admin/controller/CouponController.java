package com.enqbs.admin.controller;

import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.form.CouponForm;
import com.enqbs.admin.service.coupon.CouponService;
import com.enqbs.admin.vo.CouponVO;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
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

import java.util.Date;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Resource
    private CouponService couponService;

    @GetMapping("/list")
    public R<PageUtil<CouponVO>> couponListPage(@RequestParam(required = false) Integer productId,
                                                @RequestParam(required = false) Date startDate,
                                                @RequestParam(required = false) Date endDate,
                                                @RequestParam(required = false) Integer status,
                                                @RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                                @RequestParam(required = false, defaultValue = "DESC") SortEnum sort,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageUtil<CouponVO> couponVOListPage = couponService.couponVOListPage(productId, startDate, endDate, status,
                deleteStatus, sort, pageNum <= 0 ? 1 : pageNum, pageSize <= 0 ? 10 : pageSize);
        return R.ok(couponVOListPage);
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
            throw new ServiceException("优惠券修改失败");
        }

        return R.ok("优惠券修改成功");
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
