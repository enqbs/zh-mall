package com.enqbs.admin.convert;

import com.enqbs.admin.form.CouponForm;
import com.enqbs.admin.vo.CouponVO;
import com.enqbs.generator.pojo.Coupon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CouponConvert {

    Coupon couponForm2Coupon(CouponForm form);

    CouponVO coupon2CouponVO(Coupon coupon);

}
