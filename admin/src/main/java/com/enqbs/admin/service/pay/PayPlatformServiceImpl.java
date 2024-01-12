package com.enqbs.admin.service.pay;

import com.enqbs.admin.convert.PayConvert;
import com.enqbs.admin.vo.PayPlatformVO;
import com.enqbs.generator.dao.PayPlatformMapper;
import com.enqbs.generator.pojo.PayPlatform;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PayPlatformServiceImpl implements PayPlatformService {

    @Resource
    private PayPlatformMapper payPlatformMapper;

    @Resource
    private PayConvert payConvert;

    @Override
    public List<PayPlatformVO> getPayPlatformVOList(Set<Long> payInfoIdSet) {
        List<PayPlatform> payPlatformList = payPlatformMapper.selectListByPayInfoIdSet(payInfoIdSet);
        return payPlatformList.stream().map(p -> payConvert.payPlatform2PayPlatformVO(p)).collect(Collectors.toList());
    }

    @Override
    public PayPlatformVO getPayPlatformVO(Long payInfoId) {
        PayPlatform payPlatform = payPlatformMapper.selectByPayInfoId(payInfoId);
        return payConvert.payPlatform2PayPlatformVO(payPlatform);
    }

}
