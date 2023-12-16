package com.enqbs.app.convert;

import com.enqbs.app.pojo.vo.CouponVO;
import com.enqbs.generator.pojo.Coupon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CouponConvert {

    CouponVO coupon2CouponVO(Coupon coupon);

}
