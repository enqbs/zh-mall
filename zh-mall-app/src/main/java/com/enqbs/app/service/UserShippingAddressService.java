package com.enqbs.app.service;

import com.enqbs.app.form.UserShippingAddressForm;
import com.enqbs.app.pojo.vo.UserShippingAddressVO;

import java.util.List;

public interface UserShippingAddressService {

    /*
    * 新增收货地址
    * */
    int insertUserShippingAddress(UserShippingAddressForm form);

    /*
    * 修改收货地址
    * */
    int updateUserShippingAddress(Integer shippingAddressId, UserShippingAddressForm form);

    /*
    * 删除收货地址
    * */
    int deleteUserShippingAddress(Integer shippingAddressId);

    /*
    * 收货地址信息
    * */
    UserShippingAddressVO getUserShippingAddressVO(Integer shippingAddressId);

    /*
    * 收货地址列表
    * */
    List<UserShippingAddressVO> getUserShippingAddressVOList();

}
