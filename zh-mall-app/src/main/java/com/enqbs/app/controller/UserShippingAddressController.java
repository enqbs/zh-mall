package com.enqbs.app.controller;

import com.enqbs.app.form.UserShippingAddressForm;
import com.enqbs.app.service.UserShippingAddressService;
import com.enqbs.app.vo.UserShippingAddressVO;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.R;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserShippingAddressController {

    @Resource
    private UserShippingAddressService userShippingAddressService;

    @PostMapping("/shipping")
    public R<Void> addShippingAddress(@Valid @RequestBody UserShippingAddressForm form) {
        int insertRow = userShippingAddressService.insertUserShippingAddress(form);

        if (insertRow <= 0) {
            throw new ServiceException("新增收货地址失败");
        }

        return R.ok("新增收货地址成功");
    }

    @PutMapping("/shipping/{shippingAddressId}")
    public R<Void> updateShippingAddress(@PathVariable Integer shippingAddressId, @Valid @RequestBody UserShippingAddressForm form) {
        int updateRow = userShippingAddressService.updateUserShippingAddress(shippingAddressId, form);

        if (updateRow <= 0) {
            throw new ServiceException("修改收货地址失败");
        }

        return R.ok("修改收货地址成功");
    }

    @DeleteMapping("/shipping/{shippingAddressId}")
    public R<Void> deleteShippingAddress(@PathVariable Integer shippingAddressId) {
        int deleteRow = userShippingAddressService.deleteUserShippingAddress(shippingAddressId);

        if (deleteRow <= 0) {
            throw new ServiceException("删除收货地址失败");
        }

        return R.ok("删除收货地址成功");
    }

    @GetMapping("/shipping/{shippingAddressId}")
    public R<UserShippingAddressVO> shippingAddressDetail(@PathVariable Integer shippingAddressId) {
        UserShippingAddressVO shippingAddressVO = userShippingAddressService.getUserShippingAddressVO(shippingAddressId);
        return R.ok(shippingAddressVO);
    }

    @GetMapping("/shipping/list")
    public R<List<UserShippingAddressVO>> shippingAddressList() {
        List<UserShippingAddressVO> shippingAddressVOList = userShippingAddressService.getUserShippingAddressVOList();
        return R.ok(shippingAddressVOList);
    }

}
