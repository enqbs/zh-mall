package com.enqbs.admin.service.order;

import com.enqbs.admin.form.LogisticsInfoForm;
import com.enqbs.admin.vo.OrderLogisticsInfoVO;

import java.util.List;
import java.util.Set;

public interface OrderLogisticsInfoService {

    /*
    * 订单快递信息列表
    * */
    List<OrderLogisticsInfoVO> getOrderLogisticsInfoVOList(Set<Long> orderNoSet);

    /*
    * 订单快递信息
    * */
    OrderLogisticsInfoVO getOrderLogisticsInfoVO(Long orderNo);

    int insert(Long orderNo, LogisticsInfoForm form);

    int update(Long orderNo, LogisticsInfoForm form);

}
