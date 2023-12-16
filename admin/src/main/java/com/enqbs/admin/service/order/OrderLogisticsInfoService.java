package com.enqbs.admin.service.order;

import com.enqbs.admin.form.LogisticsInfoForm;
import com.enqbs.admin.vo.OrderLogisticsInfoVO;

import java.util.List;
import java.util.Set;

public interface OrderLogisticsInfoService {

    List<OrderLogisticsInfoVO> getOrderLogisticsInfoVOList(Set<Long> orderNoSet);

    OrderLogisticsInfoVO getOrderLogisticsInfoVO(Long orderNo);

    int insert(Long orderNo, LogisticsInfoForm form);

    int update(Long orderNo, LogisticsInfoForm form);

}
