package com.enqbs.app.service.order;

import com.enqbs.app.convert.OrderConvert;
import com.enqbs.app.pojo.vo.OrderItemVO;
import com.enqbs.app.pojo.vo.SkuParamVO;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.GsonUtil;
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
        return orderItemList.stream().map(e -> {
                    OrderItemVO orderItemVO = orderConvert.orderItem2OrderItemVO(e);
                    orderItemVO.setSkuParams(GsonUtil.json2ArrayList(e.getSkuParams(), SkuParamVO[].class));
                    return orderItemVO;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public List<OrderItemVO> getOrderItemVOList(Set<Long> orderNoSet) {
        List<OrderItem> orderItemList = orderItemMapper.selectListByOrderNoSet(orderNoSet);
        return orderItemList.stream().map(e -> {
                    OrderItemVO orderItemVO = orderConvert.orderItem2OrderItemVO(e);
                    orderItemVO.setSkuParams(GsonUtil.json2ArrayList(e.getSkuParams(), SkuParamVO[].class));
                    return orderItemVO;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public void batchInsert(Long orderNo, List<OrderItem> orderItemList) {
        int row = orderItemMapper.batchInsertByOrderItemList(orderItemList);

        if (row <= 0) {
            throw new ServiceException("订单号:" + orderNo + ",订单项保存失败");
        }
    }

}