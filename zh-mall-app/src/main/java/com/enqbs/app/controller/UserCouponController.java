package com.enqbs.app.controller;

import com.enqbs.app.pojo.vo.UserCouponVO;
import com.enqbs.app.service.user.UserCouponService;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserCouponController {

    @Resource
    private UserCouponService userCouponService;

    @GetMapping("/user/coupon/list")
    public R<PageUtil<UserCouponVO>> userCouponList(@RequestParam(required = false) Integer status,
                                                    @RequestParam(required = false, defaultValue = "DESC") SortEnum sort,
                                                    @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                    @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        if (pageNum <= 0) {
            pageNum = 1;
        }

        if (pageSize <= 0) {
            pageSize = 5;
        }

        PageUtil<UserCouponVO> pageUserCouponList = userCouponService.getUserCouponVOList(status, sort, pageNum, pageSize);
        return R.ok(pageUserCouponList);
    }

    @GetMapping("/user/coupon/{couponId}")
    public R<UserCouponVO> userCouponDetail(@PathVariable Integer couponId) {
        UserCouponVO userCouponInfo = userCouponService.getUserCouponVO(couponId);
        return R.ok(userCouponInfo);
    }

    @PostMapping("/user/coupon/{couponId}")
    public R<Map<String, Integer>> userAddCoupon(@PathVariable Integer couponId) {
        userCouponService.add(couponId);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("couponId", couponId);
        return R.ok("优惠券领取成功", resultMap);
    }

    @DeleteMapping("/user/coupon/{couponId}")
    public R<Void> userDeleteCoupon(@PathVariable Integer couponId) {
        int row = userCouponService.delete(couponId);

        if (row <= 0) {
            throw new ServiceException("优惠券删除失败");
        }

        return R.ok("优惠券删除成功");
    }

}
