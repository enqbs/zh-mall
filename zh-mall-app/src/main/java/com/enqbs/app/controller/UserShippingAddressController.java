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

    @PutMapping("/shipping/{userShippingAddressId}")
    public R<Void> updateShippingAddress(@PathVariable Integer userShippingAddressId, @Valid @RequestBody UserShippingAddressForm form) {
        int updateRow = userShippingAddressService.updateUserShippingAddress(userShippingAddressId, form);

        if (updateRow <= 0) {
            throw new ServiceException("修改收货地址失败");
        }

        return R.ok("修改收货地址成功");
    }

    @DeleteMapping("/shipping/{userShippingAddressId}")
    public R<Void> deleteShippingAddress(@PathVariable Integer userShippingAddressId) {
        int deleteRow = userShippingAddressService.deleteUserShippingAddress(userShippingAddressId);

        if (deleteRow <= 0) {
            throw new ServiceException("删除收货地址失败");
        }

        return R.ok("删除收货地址成功");
    }

    @GetMapping("/shipping/{userShippingAddressId}")
    public R<UserShippingAddressVO> getShippingAddress(@PathVariable Integer userShippingAddressId) {
        UserShippingAddressVO userShippingAddressVO = userShippingAddressService.getUserShippingAddressVO(userShippingAddressId);
        return R.ok(userShippingAddressVO);
    }

    @GetMapping("/shipping/list")
    public R<List<UserShippingAddressVO>> getShippingAddressList() {
        List<UserShippingAddressVO> userShippingAddressVOList = userShippingAddressService.getUserShippingAddressVOList();
        return R.ok(userShippingAddressVOList);
    }

}
