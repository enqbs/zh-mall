package com.enqbs.admin.convert;

import com.enqbs.admin.vo.PayInfoVO;
import com.enqbs.admin.vo.PayPlatformVO;
import com.enqbs.generator.pojo.PayInfo;
import com.enqbs.generator.pojo.PayPlatform;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PayConvert {

    @Mapping(target = "payPlatform", ignore = true)
    PayInfoVO payInfo2PayInfoVO(PayInfo payInfo);

    PayPlatformVO payPlatform2PayPlatformVO(PayPlatform payPlatform);

}
