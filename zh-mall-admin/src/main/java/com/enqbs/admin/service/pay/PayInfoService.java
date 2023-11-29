package com.enqbs.admin.service.pay;

import com.enqbs.admin.vo.PayInfoVO;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.util.PageUtil;

public interface PayInfoService {

    /*
     * 支付信息列表
     * */
    PageUtil<PayInfoVO> getPayInfoVOList(Long orderNo, Integer userId, String payType,
                                         String platform, String platformNumber, Integer status,
                                         Integer deleteStatus, SortEnum sort, Integer pageNum, Integer pageSize);

    /*
     * 支付信息
     * */
    PayInfoVO getPayInfoVO(Long id);

}
