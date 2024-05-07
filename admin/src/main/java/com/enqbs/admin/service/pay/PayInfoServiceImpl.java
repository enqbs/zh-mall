package com.enqbs.admin.service.pay;

import com.enqbs.admin.convert.PayConvert;
import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.vo.PayInfoVO;
import com.enqbs.admin.vo.PayPlatformVO;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.PayInfoMapper;
import com.enqbs.generator.pojo.PayInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PayInfoServiceImpl implements PayInfoService {

    @Resource
    private PayInfoMapper payInfoMapper;

    @Resource
    private PayPlatformService payPlatformService;

    @Resource
    private PayConvert payConvert;

    @Override
    public PageUtil<PayInfoVO> payInfoVOListPage(Long orderNo, Integer userId, String payType,
                                                 String platform, String platformNumber, Integer status,
                                                 Integer deleteStatus, SortEnum sort, Integer pageNum, Integer pageSize) {
        Long total = payInfoMapper.countByParam(orderNo, userId, payType, platform, platformNumber, status, deleteStatus);
        List<PayInfo> payInfoList = payInfoMapper.selectListParam(orderNo, userId, payType, platform, platformNumber, status, deleteStatus, sort.getSortType(), pageNum, pageSize);
        Set<Long> payInfoIdSet = payInfoList.stream().map(PayInfo::getId).collect(Collectors.toSet());
        Map<Long, PayPlatformVO> platformVOMap = payPlatformService.getPayPlatformVOList(payInfoIdSet).stream().collect(Collectors.toMap(PayPlatformVO::getPayInfoId, v -> v));
        List<PayInfoVO> payInfoVOList = payInfoList.stream().map(p -> {
                    PayInfoVO payInfoVO = payConvert.payInfo2PayInfoVO(p);
                    payInfoVO.setPayPlatform(platformVOMap.get(payInfoVO.getId()));
                    return payInfoVO;
                }
        ).collect(Collectors.toList());
        PageUtil<PayInfoVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        pageUtil.setTotal(total);
        pageUtil.setList(payInfoVOList);
        return pageUtil;
    }

    @Override
    public PayInfoVO getPayInfoVO(Long id) {
        PayInfo payInfo = payInfoMapper.selectByPrimaryKey(id);
        PayInfoVO payInfoVO = payConvert.payInfo2PayInfoVO(payInfo);

        if (ObjectUtils.isNotEmpty(payInfoVO)) {
            PayPlatformVO payPlatformVO = payPlatformService.getPayPlatformVO(id);
            payInfoVO.setPayPlatform(payPlatformVO);
        }

        return payInfoVO;
    }

}
