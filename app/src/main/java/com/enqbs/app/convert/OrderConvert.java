package com.enqbs.app.convert;

import com.enqbs.app.pojo.vo.OrderItemVO;
import com.enqbs.app.pojo.vo.OrderAddressVO;
import com.enqbs.app.pojo.vo.OrderVO;
import com.enqbs.app.pojo.vo.UserAddressVO;
import com.enqbs.generator.pojo.Order;
import com.enqbs.generator.pojo.OrderItem;
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

    @Mappings({
            @Mapping(target = "skuParams", ignore = true),
            @Mapping(target = "sharding", ignore = true)
    })
    OrderItem orderItemVO2OrderItem(OrderItemVO orderItemVO);

    @Mapping(target = "skuParams", ignore = true)
    OrderItemVO orderItem2OrderItemVO(OrderItem orderItem);

    @Mappings({
            @Mapping(target = "orderNo", ignore = true),
            @Mapping(target = "sharding", ignore = true)
    })
    OrderShippingAddress userAddressVO2OrderAddress(UserAddressVO userAddressVO);

    OrderAddressVO address2AddressVO(OrderShippingAddress address);

}
