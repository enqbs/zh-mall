package com.enqbs.admin.service.order;

import com.enqbs.admin.form.LogisticsInfoForm;
import com.enqbs.admin.vo.OrderVO;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.util.PageUtil;

public interface OrderService {

    /*
     * 订单列表
     * */
    PageUtil<OrderVO> getOrderVOList(Long orderNo, String orderSc, Integer userId,
                                     Integer paymentType, Integer status, Integer deleteStatus,
                                     SortEnum sortEnum, Integer pageNum, Integer pageSize);

    /*
     * 订单详情
     * */
    OrderVO getOrderVO(Long orderNo);

    /*
     * 订单发货、保存订单快递信息
     * */
    int insertOrderLogisticsInfo(Long orderNo, LogisticsInfoForm form);

    /*
     * 修改订单快递信息
     * */
    int updateOrderLogisticsInfo(Long orderNo, LogisticsInfoForm form);

}
