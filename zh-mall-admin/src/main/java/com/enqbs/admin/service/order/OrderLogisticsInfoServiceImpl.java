package com.enqbs.admin.service.order;

import com.enqbs.admin.form.LogisticsInfoForm;
import com.enqbs.admin.vo.OrderLogisticsInfoVO;
import com.enqbs.generator.dao.OrderLogisticsInfoMapper;
import com.enqbs.generator.pojo.OrderLogisticsInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderLogisticsInfoServiceImpl implements OrderLogisticsInfoService {

    @Resource
    private OrderLogisticsInfoMapper orderLogisticsInfoMapper;

    @Override
    public List<OrderLogisticsInfoVO> getOrderLogisticsInfoVOList(Set<Long> orderNoSet) {
        List<OrderLogisticsInfo> orderLogisticsInfoList = orderLogisticsInfoMapper.selectListByOrderNoSet(orderNoSet);
        return orderLogisticsInfoList.stream().map(this::orderLogisticsInfo2OrderLogisticsInfoVO).collect(Collectors.toList());
    }

    @Override
    public OrderLogisticsInfoVO getOrderLogisticsInfoVO(Long orderNo) {
        OrderLogisticsInfo orderLogisticsInfo = orderLogisticsInfoMapper.selectByOrderNo(orderNo);
        return orderLogisticsInfo2OrderLogisticsInfoVO(orderLogisticsInfo);
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

    private OrderLogisticsInfoVO orderLogisticsInfo2OrderLogisticsInfoVO(OrderLogisticsInfo orderLogisticsInfo) {
        OrderLogisticsInfoVO orderLogisticsInfoVO = new OrderLogisticsInfoVO();
        BeanUtils.copyProperties(orderLogisticsInfo, orderLogisticsInfoVO);
        return orderLogisticsInfoVO;
    }

}
