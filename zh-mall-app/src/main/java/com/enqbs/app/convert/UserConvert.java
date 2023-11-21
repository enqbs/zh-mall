package com.enqbs.app.convert;

import com.enqbs.app.pojo.vo.UserCouponVO;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.app.pojo.vo.UserShippingAddressVO;
import com.enqbs.generator.pojo.UserCoupon;
import com.enqbs.generator.pojo.UserShippingAddress;
import com.enqbs.security.pojo.LoginUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConvert {

    UserCouponVO userCoupon2UserCouponVO(UserCoupon userCoupon);

    UserShippingAddressVO userShippingAddress2UserShippingAddressVO(UserShippingAddress shippingAddress);

    UserInfoVO loginUser2UserInfoVO(LoginUser loginUser);

}
