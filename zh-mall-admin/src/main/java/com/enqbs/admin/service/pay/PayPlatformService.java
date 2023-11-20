package com.enqbs.admin.service.pay;

import com.enqbs.admin.vo.PayPlatformVO;

import java.util.List;
import java.util.Set;

public interface PayPlatformService {

    List<PayPlatformVO> getPayPlatformVOList(Set<Long> payInfoIdSet);

    PayPlatformVO getPayPlatformVO(Long payInfoId);

}
