package com.enqbs.admin.convert;

import com.enqbs.admin.vo.OrderItemVO;
import com.enqbs.admin.vo.OrderLogisticsInfoVO;
import com.enqbs.admin.vo.OrderAddressVO;
import com.enqbs.admin.vo.OrderVO;
import com.enqbs.generator.pojo.Order;
import com.enqbs.generator.pojo.OrderItem;
import com.enqbs.generator.pojo.OrderLogisticsInfo;
import com.enqbs.generator.pojo.OrderShippingAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderConvert {

    @Mappings({
            @Mapping(target = "address", ignore = true),
            @Mapping(target = "logisticsInfo", ignore = true),
            @Mapping(target = "orderItemList", ignore = true)
    })
    OrderVO order2OrderVO(Order order);

    @Mapping(target = "skuParams", ignore = true)
    OrderItemVO orderItem2OrderItemVO(OrderItem orderItem);

    OrderAddressVO orderAddress2OrderAddressVO(OrderShippingAddress orderAddress);

    OrderLogisticsInfoVO orderLogisticsInfo2OrderLogisticsInfoVO(OrderLogisticsInfo orderLogisticsInfo);

}
