package com.enqbs.app.service.impl;

import com.enqbs.app.form.UserShippingAddressForm;
import com.enqbs.app.service.UserService;
import com.enqbs.app.service.UserShippingAddressService;
import com.enqbs.app.vo.UserInfoVO;
import com.enqbs.app.vo.UserShippingAddressVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.UserShippingAddressMapper;
import com.enqbs.generator.pojo.UserShippingAddress;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserShippingAddressServiceImpl implements UserShippingAddressService {

    @Resource
    private UserService userService;

    @Resource
    private UserShippingAddressMapper userShippingAddressMapper;

    @Override
    public int insertUserShippingAddress(UserShippingAddressForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        UserShippingAddress userShippingAddress = new UserShippingAddress();
        userShippingAddress.setUserId(userInfoVO.getUserId());
        userShippingAddress.setName(form.getName());
        userShippingAddress.setTelNo(form.getTelNo());
        userShippingAddress.setAddress(form.getAddress());
        userShippingAddress.setDetailAddress(form.getDetailAddress());
        return userShippingAddressMapper.insertSelective(userShippingAddress);
    }

    @Override
    public int updateUserShippingAddress(Integer userShippingAddressId, UserShippingAddressForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        UserShippingAddress userShippingAddress = userShippingAddressMapper.selectByPrimaryKey(userShippingAddressId);

        if (ObjectUtils.isEmpty(userShippingAddress) ||
                !userShippingAddress.getUserId().equals(userInfoVO.getUserId()) ||
                userShippingAddress.getDeleteStatus().equals(Constants.IS_DELETE)) {
            throw new ServiceException("收货地址不存在");
        }

        userShippingAddress.setName(form.getName());
        userShippingAddress.setTelNo(form.getTelNo());
        userShippingAddress.setAddress(form.getAddress());
        userShippingAddress.setDetailAddress(form.getDetailAddress());
        return userShippingAddressMapper.updateByPrimaryKeySelective(userShippingAddress);
    }

    @Override
    public int deleteUserShippingAddress(Integer userShippingAddressId) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        UserShippingAddress userShippingAddress = userShippingAddressMapper.selectByPrimaryKey(userShippingAddressId);

        if (ObjectUtils.isEmpty(userShippingAddress) ||
                !userShippingAddress.getUserId().equals(userInfoVO.getUserId()) ||
                userShippingAddress.getDeleteStatus().equals(Constants.IS_DELETE)) {
            throw new ServiceException("收货地址不存在");
        }

        userShippingAddress.setDeleteStatus(Constants.IS_DELETE);
        return userShippingAddressMapper.updateByPrimaryKeySelective(userShippingAddress);
    }

    @Override
    public UserShippingAddressVO getUserShippingAddressVO(Integer userShippingAddressId) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        UserShippingAddress userShippingAddress = userShippingAddressMapper.selectByPrimaryKey(userShippingAddressId);

        if (ObjectUtils.isEmpty(userShippingAddress) ||
                !userShippingAddress.getUserId().equals(userInfoVO.getUserId()) ||
                userShippingAddress.getDeleteStatus().equals(Constants.IS_DELETE)) {
            throw new ServiceException("收货地址不存在");
        }

        return userShippingAddress2UserShippingAddressVO(userShippingAddress);
    }

    @Override
    public List<UserShippingAddressVO> getUserShippingAddressVOList() {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        List<UserShippingAddress> userShippingAddressList = userShippingAddressMapper.selectListByUserId(userInfoVO.getUserId());
        return userShippingAddressList.stream().map(this::userShippingAddress2UserShippingAddressVO).collect(Collectors.toList());
    }

    private UserShippingAddressVO userShippingAddress2UserShippingAddressVO(UserShippingAddress userShippingAddress) {
        UserShippingAddressVO userShippingAddressVO = new UserShippingAddressVO();
        BeanUtils.copyProperties(userShippingAddress, userShippingAddressVO);
        return userShippingAddressVO;
    }

}
