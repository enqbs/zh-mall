package com.enqbs.app.service.user;

import com.enqbs.app.convert.UserConvert;
import com.enqbs.app.form.UserShippingAddressForm;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.app.pojo.vo.UserShippingAddressVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.UserShippingAddressMapper;
import com.enqbs.generator.pojo.UserShippingAddress;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserShippingAddressServiceImpl implements UserShippingAddressService {

    @Resource
    private UserShippingAddressMapper userShippingAddressMapper;

    @Resource
    private UserService userService;

    @Resource
    private UserConvert userConvert;

    @Override
    public int insert(UserShippingAddressForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        UserShippingAddress shippingAddress = new UserShippingAddress();
        shippingAddress.setUserId(userInfoVO.getUserId());
        shippingAddress.setName(form.getName());
        shippingAddress.setTelNo(form.getTelNo());
        shippingAddress.setAddress(form.getAddress());
        shippingAddress.setDetailAddress(form.getDetailAddress());
        return userShippingAddressMapper.insertSelective(shippingAddress);
    }

    @Override
    public int update(Integer shippingAddressId, UserShippingAddressForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        UserShippingAddress shippingAddress = userShippingAddressMapper.selectByPrimaryKeyOrUserIdOrDeleteStatus(
                shippingAddressId, userInfoVO.getUserId(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isEmpty(shippingAddress)) {
            throw new ServiceException("收货地址不存在");
        }

        shippingAddress.setName(form.getName());
        shippingAddress.setTelNo(form.getTelNo());
        shippingAddress.setAddress(form.getAddress());
        shippingAddress.setDetailAddress(form.getDetailAddress());
        return userShippingAddressMapper.updateByPrimaryKeySelective(shippingAddress);
    }

    @Override
    public int delete(Integer shippingAddressId) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        UserShippingAddress shippingAddress = userShippingAddressMapper.selectByPrimaryKeyOrUserIdOrDeleteStatus(
                shippingAddressId, userInfoVO.getUserId(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isEmpty(shippingAddress)) {
            throw new ServiceException("收货地址不存在");
        }

        shippingAddress.setDeleteStatus(Constants.IS_DELETE);
        return userShippingAddressMapper.updateByPrimaryKeySelective(shippingAddress);
    }

    @Override
    public UserShippingAddressVO getUserShippingAddressVO(Integer shippingAddressId) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        UserShippingAddress shippingAddress = userShippingAddressMapper.selectByPrimaryKeyOrUserIdOrDeleteStatus(
                shippingAddressId, userInfoVO.getUserId(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isEmpty(shippingAddress)) {
            throw new ServiceException("收货地址不存在");
        }

        return userConvert.userShippingAddress2UserShippingAddressVO(shippingAddress);
    }

    @Override
    public List<UserShippingAddressVO> getUserShippingAddressVOList() {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        List<UserShippingAddress> userShippingAddressList = userShippingAddressMapper.selectListByUserId(userInfoVO.getUserId());
        return userShippingAddressList.stream().map(e -> userConvert.userShippingAddress2UserShippingAddressVO(e)).collect(Collectors.toList());
    }

}
