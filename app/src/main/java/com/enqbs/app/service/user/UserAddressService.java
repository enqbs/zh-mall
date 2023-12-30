package com.enqbs.app.service.user;

import com.enqbs.app.form.UserShippingAddressForm;
import com.enqbs.app.pojo.vo.UserAddressVO;

import java.util.List;

public interface UserAddressService {

    /*
     * 新增收货地址
     * */
    int insert(UserShippingAddressForm form);

    /*
     * 修改收货地址
     * */
    int update(Integer addressId, UserShippingAddressForm form);

    /*
     * 删除收货地址
     * */
    int delete(Integer addressId);

    /*
     * 收货地址信息
     * */
    UserAddressVO getAddressVO(Integer addressId);

    /*
     * 收货地址列表
     * */
    List<UserAddressVO> getAddressVOList();

}
