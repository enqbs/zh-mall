package com.enqbs.admin.service.pay;

import com.enqbs.admin.vo.PayInfoVO;
import com.enqbs.admin.vo.PayPlatformVO;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.PayInfoMapper;
import com.enqbs.generator.pojo.PayInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
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

    @Override
    public PageUtil<PayInfoVO> getPayInfoVOList(Long orderNo, Integer userId, String payType,
                                                String platform, String platformNumber, Integer status,
                                                Integer deleteStatus, SortEnum sortEnum, Integer pageNum, Integer pageSize) {
        PageUtil<PayInfoVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        long total = 0L;
        List<PayInfo> payInfoList = payInfoMapper.selectListParam(orderNo, userId, payType, platform, platformNumber, status, deleteStatus, sortEnum.getSortType(), pageNum, pageSize);

        if (CollectionUtils.isEmpty(payInfoList)) {
            pageUtil.setTotal(total);
            pageUtil.setList(Collections.emptyList());
            return pageUtil;
        }

        total = payInfoMapper.countByParam(orderNo, userId, payType, platform, platformNumber, status, deleteStatus);
        Set<Long> payInfoIdSet = payInfoList.stream().map(PayInfo::getId).collect(Collectors.toSet());
        Map<Long, PayPlatformVO> platformVOMap = payPlatformService
                .getPayPlatformVOList(payInfoIdSet).stream().collect(Collectors.toMap(PayPlatformVO::getPayInfoId, v -> v));
        List<PayInfoVO> payInfoVOList = payInfoList.stream().map(e -> {
            PayInfoVO payInfoVO = payInfo2PayInfoVO(e);
            payInfoVO.setPayPlatform(platformVOMap.get(payInfoVO.getId()));
            return payInfoVO;
        }).collect(Collectors.toList());
        pageUtil.setTotal(total);
        pageUtil.setList(payInfoVOList);
        return pageUtil;
    }

    @Override
    public PayInfoVO getPayInfoVO(Long id) {
        PayInfoVO payInfoVO = new PayInfoVO();
        PayInfo payInfo = payInfoMapper.selectByPrimaryKey(id);

        if (ObjectUtils.isEmpty(payInfo)) {
            return payInfoVO;
        }

        payInfoVO = payInfo2PayInfoVO(payInfo);
        PayPlatformVO payPlatformVO = payPlatformService.getPayPlatformVO(id);

        if (ObjectUtils.isNotEmpty(payPlatformVO)) {
            payInfoVO.setPayPlatform(payPlatformVO);
        }

        return payInfoVO;
    }

    private PayInfoVO payInfo2PayInfoVO(PayInfo payInfo) {
        PayInfoVO payInfoVO = new PayInfoVO();
        BeanUtils.copyProperties(payInfo, payInfoVO);
        return payInfoVO;
    }

}
