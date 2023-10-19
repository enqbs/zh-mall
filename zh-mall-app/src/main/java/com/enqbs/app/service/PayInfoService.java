package com.enqbs.app.service;

import com.enqbs.generator.pojo.PayInfo;
import com.enqbs.pay.enums.PayTypeEnum;

public interface PayInfoService {

    /*
    * 新增支付信息
    * */
    PayInfo insertPayInfo(Long orderNo);

    /*
    * 更新支付信息
    * */
    void updatePayInfo(PayTypeEnum payTypeEnum, String orderNo, String platformNo);

}
