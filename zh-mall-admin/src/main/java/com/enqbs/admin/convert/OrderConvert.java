package com.enqbs.admin.convert;

import com.enqbs.admin.vo.OrderItemVO;
import com.enqbs.admin.vo.OrderLogisticsInfoVO;
import com.enqbs.admin.vo.OrderShippingAddressVO;
import com.enqbs.admin.vo.OrderVO;
import com.enqbs.generator.pojo.Order;
import com.enqbs.generator.pojo.OrderItem;
import com.enqbs.generator.pojo.OrderLogisticsInfo;
import com.enqbs.generator.pojo.OrderShippingAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderConvert {

    OrderVO order2OrderVO(Order order);

    OrderItemVO orderItem2OrderItemVO(OrderItem orderItem);

    OrderShippingAddressVO orderShippingAddress2OrderShippingAddressVO(OrderShippingAddress orderShippingAddress);

    OrderLogisticsInfoVO orderLogisticsInfo2OrderLogisticsInfoVO(OrderLogisticsInfo orderLogisticsInfo);

}
