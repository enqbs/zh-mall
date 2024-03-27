package com.enqbs.admin.service.order;

import com.enqbs.admin.convert.OrderConvert;
import com.enqbs.admin.form.LogisticsInfoForm;
import com.enqbs.admin.vo.OrderLogisticsInfoVO;
import com.enqbs.generator.dao.OrderLogisticsInfoMapper;
import com.enqbs.generator.pojo.OrderLogisticsInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderLogisticsInfoServiceImpl implements OrderLogisticsInfoService {

    @Resource
    private OrderLogisticsInfoMapper orderLogisticsInfoMapper;

    @Resource
    private OrderConvert orderConvert;

    @Override
    public List<OrderLogisticsInfoVO> getOrderLogisticsInfoVOList(Set<Long> orderNoSet) {
        if (CollectionUtils.isEmpty(orderNoSet)) {
            return Collections.emptyList();
        }

        List<OrderLogisticsInfo> orderLogisticsInfoList = orderLogisticsInfoMapper.selectListByOrderNoSet(orderNoSet);
        return CollectionUtils.isEmpty(orderLogisticsInfoList) ?
                Collections.emptyList() : orderLogisticsInfoList.stream().map(o -> orderConvert.orderLogisticsInfo2OrderLogisticsInfoVO(o)).collect(Collectors.toList());
    }

    @Override
    public OrderLogisticsInfoVO getOrderLogisticsInfoVO(Long orderNo) {
        OrderLogisticsInfo orderLogisticsInfo = orderLogisticsInfoMapper.selectByOrderNo(orderNo);
        return ObjectUtils.isEmpty(orderLogisticsInfo) ? null : orderConvert.orderLogisticsInfo2OrderLogisticsInfoVO(orderLogisticsInfo);
    }

    @Override
    public int insert(Long orderNo, LogisticsInfoForm form) {
        OrderLogisticsInfo orderLogisticsInfo = new OrderLogisticsInfo();
        orderLogisticsInfo.setOrderNo(orderNo);
        orderLogisticsInfo.setLogisticsNo(form.getLogisticsNo());
        orderLogisticsInfo.setLogisticsTitle(form.getLogisticsTitle());
        return orderLogisticsInfoMapper.insertSelective(orderLogisticsInfo);
    }

    @Override
    public int update(Long orderNo, LogisticsInfoForm form) {
        OrderLogisticsInfo orderLogisticsInfo = orderLogisticsInfoMapper.selectByOrderNo(orderNo);
        orderLogisticsInfo.setLogisticsNo(form.getLogisticsNo());
        orderLogisticsInfo.setLogisticsTitle(form.getLogisticsTitle());
        return orderLogisticsInfoMapper.updateByPrimaryKeySelective(orderLogisticsInfo);
    }

}
