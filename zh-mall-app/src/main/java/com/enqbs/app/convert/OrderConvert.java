package com.enqbs.app.convert;

import com.enqbs.app.pojo.vo.OrderItemVO;
import com.enqbs.app.pojo.vo.OrderShippingAddressVO;
import com.enqbs.app.pojo.vo.OrderVO;
import com.enqbs.app.pojo.vo.UserShippingAddressVO;
import com.enqbs.generator.pojo.Order;
import com.enqbs.generator.pojo.OrderItem;
import com.enqbs.generator.pojo.OrderShippingAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderConvert {

    OrderVO order2OrderVO(Order order);

    OrderItem orderItemVO2OrderItem(OrderItemVO orderItemVO);

    OrderItemVO orderItem2OrderItemVO(OrderItem orderItem);

    OrderShippingAddress userShippingAddressVO2OrderShippingAddress(UserShippingAddressVO userShippingAddressVO);

    OrderShippingAddressVO orderShippingAddress2OrderShippingAddressVO(OrderShippingAddress orderShippingAddress);

}
