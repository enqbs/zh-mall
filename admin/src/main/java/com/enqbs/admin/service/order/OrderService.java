package com.enqbs.admin.service.order;

import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.form.LogisticsInfoForm;
import com.enqbs.admin.vo.OrderVO;
import com.enqbs.common.util.PageUtil;

public interface OrderService {

    /**
     * 订单列表（分页）
     *
     * @param orderNo 订单号
     * @param orderSc 订单流水号
     * @param userId 用户 ID
     * @param paymentType 订单支付方式（平台）
     * @param status 订单状态
     * @param deleteStatus 软删除标识
     * @param sort 排序
     * @param pageNum pn
     * @param pageSize ps
     * @return 分页信息
     */
    PageUtil<OrderVO> orderVOListPage(Long orderNo, String orderSc, Integer userId, Integer paymentType, Integer status,
                                      Integer deleteStatus, SortEnum sort, Integer pageNum, Integer pageSize);

    /**
     * 订单详情
     *
     * @param orderNo 订单号
     * @return 订单详情
     */
    OrderVO getOrderVO(Long orderNo);

    /**
     * 订单发货、保存订单快递信息
     *
     * @param orderNo 订单号
     * @param form 订单快递信息
     */
    void shipment(Long orderNo, LogisticsInfoForm form);

}
