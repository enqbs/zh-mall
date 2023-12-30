package com.enqbs.admin.convert;

import com.enqbs.admin.form.CouponForm;
import com.enqbs.admin.vo.CouponVO;
import com.enqbs.generator.pojo.Coupon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CouponConvert {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "deleteStatus", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updateTime", ignore = true)
    })
    Coupon form2Coupon(CouponForm form);

    CouponVO coupon2CouponVO(Coupon coupon);

}
