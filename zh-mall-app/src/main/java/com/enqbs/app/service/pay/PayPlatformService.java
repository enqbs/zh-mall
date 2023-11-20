package com.enqbs.app.service.pay;

import com.enqbs.pay.enums.PayTypeEnum;

public interface PayPlatformService {

    void insert(Long payInfoId, PayTypeEnum payTypeEnum, String orderNo, String platformNo);

}
