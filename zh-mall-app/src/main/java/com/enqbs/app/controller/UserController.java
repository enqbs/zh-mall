package com.enqbs.app.controller;

import com.enqbs.app.form.CartForm;
import com.enqbs.app.form.UserShippingAddressForm;
import com.enqbs.app.pojo.vo.CartVO;
import com.enqbs.app.pojo.vo.UserCouponVO;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.app.pojo.vo.UserShippingAddressVO;
import com.enqbs.app.service.user.CartService;
import com.enqbs.app.service.user.UserCouponService;
import com.enqbs.app.service.user.UserService;
import com.enqbs.app.service.user.UserShippingAddressService;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import com.enqbs.security.service.TokenService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private TokenService tokenService;
    @Resource
    private UserService userService;
    @Resource
    private UserShippingAddressService shippingAddressService;
    @Resource
    private CartService cartService;
    @Resource
    private UserCouponService userCouponService;

    @GetMapping("/info")
    public R<Map<String, Object>> userInfo(@RequestHeader String token) {
        String newToken;

        try {
            newToken = tokenService.refreshToken(token).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        UserInfoVO userInfo = userService.getUserInfoVO();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userInfo", userInfo);
        resultMap.put("token", newToken);
        return R.ok(resultMap);
    }

    @PostMapping("/shipping")
    public R<Void> addShippingAddress(@Valid @RequestBody UserShippingAddressForm form) {
        int row = shippingAddressService.insert(form);

        if (row <= 0) {
            throw new ServiceException("新增收货地址失败");
        }

        return R.ok("新增收货地址成功");
    }

    @PutMapping("/shipping/{shippingAddressId}")
    public R<Void> updateShippingAddress(@PathVariable Integer shippingAddressId, @Valid @RequestBody UserShippingAddressForm form) {
        int row = shippingAddressService.update(shippingAddressId, form);

        if (row <= 0) {
            throw new ServiceException("修改收货地址失败");
        }

        return R.ok("修改收货地址成功");
    }

    @DeleteMapping("/shipping/{shippingAddressId}")
    public R<Void> deleteShippingAddress(@PathVariable Integer shippingAddressId) {
        int row = shippingAddressService.delete(shippingAddressId);

        if (row <= 0) {
            throw new ServiceException("删除收货地址失败");
        }

        return R.ok("删除收货地址成功");
    }

    @GetMapping("/shipping/{shippingAddressId}")
    public R<UserShippingAddressVO> shippingAddressDetail(@PathVariable Integer shippingAddressId) {
        UserShippingAddressVO shippingAddressInfo = shippingAddressService.getUserShippingAddressVO(shippingAddressId);
        return R.ok(shippingAddressInfo);
    }

    @GetMapping("/shipping/list")
    public R<List<UserShippingAddressVO>> shippingAddressList() {
        List<UserShippingAddressVO> shippingAddressList = shippingAddressService.getUserShippingAddressVOList();
        return R.ok(shippingAddressList);
    }

    @GetMapping("/cart")
    public R<CartVO> cart() {
        CartVO cartInfo = cartService.getCartVO();
        return R.ok(cartInfo);
    }

    @PostMapping("/cart")
    public R<CartVO> addCart(@Valid @RequestBody CartForm form) {
        CartVO cartInfo = cartService.add(form);
        return R.ok(cartInfo);
    }

    @PutMapping("/cart")
    public R<CartVO> updateCart(@Valid @RequestBody CartForm form) {
        CartVO cartInfo = cartService.update(form);
        return R.ok(cartInfo);
    }

    @DeleteMapping("/cart/{skuId}")
    public R<CartVO> deleteCart(@PathVariable Integer skuId) {
        CartVO cartInfo = cartService.delete(skuId);
        return R.ok(cartInfo);
    }

    @PutMapping("/cart/selected")
    public R<CartVO> updateCartSelectedAll() {
        CartVO cartInfo = cartService.updateSelectedAll();
        return R.ok(cartInfo);
    }

    @PutMapping("/cart/not-selected")
    public R<CartVO> updateCartNotSelectedAll() {
        CartVO cartInfo = cartService.updateNotSelectedAll();
        return R.ok(cartInfo);
    }

    @DeleteMapping("/cart")
    public R<CartVO> clearCart() {
        CartVO cartInfo = cartService.clear();
        return R.ok(cartInfo);
    }

    @GetMapping("/coupon/list")
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

    @GetMapping("/coupon/{couponId}")
    public R<UserCouponVO> userCouponDetail(@PathVariable Integer couponId) {
        UserCouponVO userCouponInfo = userCouponService.getUserCouponVO(couponId);
        return R.ok(userCouponInfo);
    }

    @PostMapping("/coupon/{couponId}")
    public R<Map<String, Integer>> userAddCoupon(@PathVariable Integer couponId) {
        userCouponService.add(couponId);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("couponId", couponId);
        return R.ok("优惠券领取成功", resultMap);
    }

    @DeleteMapping("/coupon/{couponId}")
    public R<Void> userDeleteCoupon(@PathVariable Integer couponId) {
        int row = userCouponService.delete(couponId);

        if (row <= 0) {
            throw new ServiceException("优惠券删除失败");
        }

        return R.ok("优惠券删除成功");
    }

}
