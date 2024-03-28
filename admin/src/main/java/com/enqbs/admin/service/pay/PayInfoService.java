package com.enqbs.admin.service.pay;

import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.vo.PayInfoVO;
import com.enqbs.common.util.PageUtil;

public interface PayInfoService {

    /**
     * 支付信息列表
     *
     * @param orderNo 订单号
     * @param userId 用户 ID
     * @param payType 支付方式
     * @param platform 支付平台
     * @param platformNumber 支付平台流水号
     * @param status 支付状态
     * @param deleteStatus 软删除标识
     * @param sort 排序
     * @param pageNum pn
     * @param pageSize ps
     * @return 分页信息
     */
    PageUtil<PayInfoVO> payInfoVOListPage(Long orderNo, Integer userId, String payType, String platform, String platformNumber,
                                          Integer status, Integer deleteStatus, SortEnum sort, Integer pageNum, Integer pageSize);

    /**
     * 支付信息
     *
     * @param id 支付信息 ID
     * @return 支付信息
     */
    PayInfoVO getPayInfoVO(Long id);

}
