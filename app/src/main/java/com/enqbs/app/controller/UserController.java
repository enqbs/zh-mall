package com.enqbs.app.controller;

import com.enqbs.app.form.CartForm;
import com.enqbs.app.form.UserShippingAddressForm;
import com.enqbs.app.pojo.vo.CartVO;
import com.enqbs.app.pojo.vo.UserCouponVO;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.app.pojo.vo.UserAddressVO;
import com.enqbs.app.service.user.CartService;
import com.enqbs.app.service.user.UserCouponService;
import com.enqbs.app.service.user.UserService;
import com.enqbs.app.service.user.UserAddressService;
import com.enqbs.app.enums.SortEnum;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import com.enqbs.security.service.TokenService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
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
    private UserAddressService userAddressService;
    @Resource
    private CartService cartService;
    @Resource
    private UserCouponService userCouponService;

    @GetMapping("/info")
    public R<Map<String, Object>> userInfo(@RequestHeader(required = false) String token) {
        Map<String, Object> resultMap = new HashMap<>();
        String newToken = token;

        if (StringUtils.isNotEmpty(newToken)) {
            newToken = tokenService.refreshToken(newToken);
            UserInfoVO userInfo = userService.getUserInfoVO();
            resultMap.put("userInfo", userInfo);
            resultMap.put("token", newToken);
        }

        return R.ok(resultMap);
    }

    @PostMapping("/shipping")
    public R<Void> addAddress(@Valid @RequestBody UserShippingAddressForm form) {
        int row = userAddressService.insert(form);

        if (row <= 0) {
            throw new ServiceException("新增收货地址失败");
        }

        return R.ok("新增收货地址成功");
    }

    @PutMapping("/shipping/{addressId}")
    public R<Void> updateAddress(@PathVariable Integer addressId, @Valid @RequestBody UserShippingAddressForm form) {
        int row = userAddressService.update(addressId, form);

        if (row <= 0) {
            throw new ServiceException("修改收货地址失败");
        }

        return R.ok("修改收货地址成功");
    }

    @DeleteMapping("/shipping/{addressId}")
    public R<Void> deleteAddress(@PathVariable Integer addressId) {
        int row = userAddressService.delete(addressId);

        if (row <= 0) {
            throw new ServiceException("删除收货地址失败");
        }

        return R.ok("删除收货地址成功");
    }

    @GetMapping("/shipping/{addressId}")
    public R<UserAddressVO> addressDetail(@PathVariable Integer addressId) {
        UserAddressVO addressInfo = userAddressService.getAddressVO(addressId);
        return R.ok(addressInfo);
    }

    @GetMapping("/shipping/list")
    public R<List<UserAddressVO>> addressList() {
        List<UserAddressVO> addressVOList = userAddressService.getAddressVOList();
        return R.ok(addressVOList);
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
    public R<PageUtil<UserCouponVO>> couponListPage(@RequestParam(required = false) Integer status,
                                                    @RequestParam(required = false, defaultValue = "DESC") SortEnum sort,
                                                    @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                    @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        PageUtil<UserCouponVO> couponVOListPage = userCouponService.couponVOListPage(status, sort, pageNum <= 0 ? 1 : pageNum, pageSize <= 0 ? 5 : pageSize);
        return R.ok(couponVOListPage);
    }

    @GetMapping("/coupon/{couponId}")
    public R<UserCouponVO> couponDetail(@PathVariable Integer couponId) {
        UserCouponVO couponInfo = userCouponService.getCouponVO(couponId);
        return R.ok(couponInfo);
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
