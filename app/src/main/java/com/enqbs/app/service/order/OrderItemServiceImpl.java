package com.enqbs.app.service.order;

import com.enqbs.app.convert.OrderConvert;
import com.enqbs.app.pojo.vo.OrderItemVO;
import com.enqbs.app.pojo.vo.SkuParamVO;
import com.enqbs.common.exception.ServiceException;
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
        return orderItemList2OrderItemVOList(orderItemList);
    }

    @Override
    public List<OrderItemVO> getOrderItemVOList(Set<Long> orderNoSet) {
        List<OrderItem> orderItemList = CollectionUtils.isEmpty(orderNoSet) ? Collections.emptyList() : orderItemMapper.selectListByOrderNoSet(orderNoSet);
        return orderItemList2OrderItemVOList(orderItemList);
    }

    @Override
    public void batchInsert(Long orderNo, List<OrderItem> orderItemList) {
        int row = orderItemMapper.batchInsertByOrderItemList(orderItemList);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",订单项保存失败");
        }
    }

    private List<OrderItemVO> orderItemList2OrderItemVOList(List<OrderItem> orderItemList) {
        return orderItemList.stream().map(o -> {
                    OrderItemVO orderItemVO = orderConvert.orderItem2OrderItemVO(o);
                    orderItemVO.setSkuParams(GsonUtil.json2ArrayList(o.getSkuParams(), SkuParamVO[].class));
                    return orderItemVO;
                }
        ).toList();
    }

}
