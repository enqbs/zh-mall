package com.enqbs.admin.service.order;

import com.enqbs.admin.convert.OrderConvert;
import com.enqbs.admin.vo.OrderItemVO;
import com.enqbs.admin.vo.SkuParamVO;
import com.enqbs.common.util.GsonUtil;
import com.enqbs.generator.dao.OrderItemMapper;
import com.enqbs.generator.pojo.OrderItem;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private OrderConvert orderConvert;

    @Override
    public List<OrderItemVO> getOrderItemVOList(Long orderNo) {
        List<OrderItem> orderItemList = orderItemMapper.selectListByOrderNo(orderNo);
        return CollectionUtils.isEmpty(orderItemList) ? Collections.emptyList() : orderItemList.stream().map(o -> {
                    OrderItemVO orderItemVO = orderConvert.orderItem2OrderItemVO(o);
                    orderItemVO.setSkuParams(GsonUtil.json2ArrayList(o.getSkuParams(), SkuParamVO[].class));
                    return orderItemVO;
                }
        ).toList();
    }

    @Override
    public List<OrderItemVO> getOrderItemVOList(Set<Long> orderNoSet) {
        List<OrderItem> orderItemList = CollectionUtils.isEmpty(orderNoSet) ? Collections.emptyList() : orderItemMapper.selectListByOrderNoSet(orderNoSet);

        if (CollectionUtils.isEmpty(orderItemList)) {
            return Collections.emptyList();
        }

        return orderItemList.stream().map(o -> {
                    OrderItemVO orderItemVO = orderConvert.orderItem2OrderItemVO(o);
                    orderItemVO.setSkuParams(GsonUtil.json2ArrayList(o.getSkuParams(), SkuParamVO[].class));
                    return orderItemVO;
                }
        ).toList();
    }

}
