package com.enqbs.app.service.user;

import com.enqbs.app.convert.UserConvert;
import com.enqbs.app.form.UserShippingAddressForm;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.app.pojo.vo.UserAddressVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.generator.dao.UserShippingAddressMapper;
import com.enqbs.generator.pojo.UserShippingAddress;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Resource
    private UserShippingAddressMapper userShippingAddressMapper;

    @Resource
    private UserService userService;

    @Resource
    private UserConvert userConvert;

    @Override
    public int insert(UserShippingAddressForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        UserShippingAddress address = userConvert.form2UserAddress(form);
        address.setUserId(userInfoVO.getUserId());
        return userShippingAddressMapper.insertSelective(address);
    }

    @Override
    public int update(Integer addressId, UserShippingAddressForm form) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Integer exist = userShippingAddressMapper.existByPrimaryKeyAndUserId(addressId, userInfoVO.getUserId());

        if (ObjectUtils.isEmpty(exist)) {
            throw new ServiceException("收货地址不存在");
        }

        UserShippingAddress address = userConvert.form2UserAddress(form);
        address.setId(addressId);
        return userShippingAddressMapper.updateByPrimaryKeySelective(address);
    }

    @Override
    public int delete(Integer addressId) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        Integer exist = userShippingAddressMapper.existByPrimaryKeyAndUserId(addressId, userInfoVO.getUserId());

        if (ObjectUtils.isEmpty(exist)) {
            throw new ServiceException("收货地址不存在");
        }

        UserShippingAddress address = new UserShippingAddress();
        address.setId(addressId);
        address.setDeleteStatus(Constants.IS_DELETE);
        return userShippingAddressMapper.updateByPrimaryKeySelective(address);
    }

    @Override
    public UserAddressVO getAddressVO(Integer addressId) {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        UserShippingAddress address = userShippingAddressMapper.selectByPrimaryKeyOrUserIdOrDeleteStatus(addressId,
                userInfoVO.getUserId(), Constants.IS_NOT_DELETE);

        if (ObjectUtils.isEmpty(address)) {
            throw new ServiceException("收货地址不存在");
        }

        return userConvert.address2AddressVO(address);
    }

    @Override
    public List<UserAddressVO> getAddressVOList() {
        UserInfoVO userInfoVO = userService.getUserInfoVO();
        List<UserShippingAddress> addressList = userShippingAddressMapper.selectListByUserId(userInfoVO.getUserId());
        return addressList.stream().map(a -> userConvert.address2AddressVO(a)).toList();
    }

}
