package com.enqbs.admin.service.order;

import com.enqbs.admin.convert.OrderConvert;
import com.enqbs.admin.vo.OrderItemVO;
import com.enqbs.generator.dao.OrderItemMapper;
import com.enqbs.generator.pojo.OrderItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private OrderConvert orderConvert;

    @Override
    public List<OrderItemVO> getOrderItemVOList(Long orderNo) {
        List<OrderItem> orderItemList = orderItemMapper.selectListByOrderNo(orderNo);
        return orderItemList.stream().map(e -> orderConvert.orderItem2OrderItemVO(e)).collect(Collectors.toList());
    }

    @Override
    public List<OrderItemVO> getOrderItemVOList(Set<Long> orderNoSet) {
        List<OrderItem> orderItemList = orderItemMapper.selectListByOrderNoSet(orderNoSet);
        return orderItemList.stream().map(e -> orderConvert.orderItem2OrderItemVO(e)).collect(Collectors.toList());
    }

}
