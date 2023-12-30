package com.enqbs.app.convert;

import com.enqbs.app.form.UserShippingAddressForm;
import com.enqbs.app.pojo.vo.UserCouponVO;
import com.enqbs.app.pojo.vo.UserInfoVO;
import com.enqbs.app.pojo.vo.UserAddressVO;
import com.enqbs.generator.pojo.UserCoupon;
import com.enqbs.generator.pojo.UserShippingAddress;
import com.enqbs.security.pojo.LoginUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserConvert {

    @Mapping(target = "coupon", ignore = true)
    UserCouponVO coupon2CouponVO(UserCoupon coupon);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "userId", ignore = true),
            @Mapping(target = "defaultStatus", ignore = true),
            @Mapping(target = "deleteStatus", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updateTime", ignore = true)
    })
    UserShippingAddress form2UserAddress(UserShippingAddressForm form);

    UserAddressVO address2AddressVO(UserShippingAddress address);

    UserInfoVO loginUser2UserInfoVO(LoginUser loginUser);

}
