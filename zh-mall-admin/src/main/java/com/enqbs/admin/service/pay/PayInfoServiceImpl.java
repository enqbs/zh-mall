package com.enqbs.admin.service.pay;

import com.enqbs.admin.vo.PayInfoVO;
import com.enqbs.admin.vo.PayPlatformVO;
import com.enqbs.common.constant.Constants;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.dao.PayInfoMapper;
import com.enqbs.generator.dao.PayPlatformMapper;
import com.enqbs.generator.pojo.PayInfo;
import com.enqbs.generator.pojo.PayPlatform;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PayInfoServiceImpl implements PayInfoService {

    @Resource
    private PayInfoMapper payInfoMapper;

    @Resource
    private PayPlatformMapper payPlatformMapper;

    @Override
    public PageUtil<PayInfoVO> getPayInfoVOList(Long orderNo, Integer userId, String payType,
                                                String platform, String platformNumber, Integer status,
                                                Integer deleteStatus, SortEnum sortEnum, Integer pageNum, Integer pageSize) {
        PageUtil<PayInfoVO> pageUtil = new PageUtil<>();
        pageUtil.setNum(pageNum);
        pageUtil.setSize(pageSize);
        long total = 0L;
        List<PayInfoVO> payInfoVOList = new ArrayList<>();
        List<PayInfo> payInfoList = payInfoMapper.selectListParam(orderNo, userId, payType, platform, platformNumber, status, deleteStatus, sortEnum.getSortType(), pageNum, pageSize);

        if (CollectionUtils.isEmpty(payInfoList)) {
            pageUtil.setTotal(total);
            pageUtil.setList(payInfoVOList);
            return pageUtil;
        }

        total = payInfoMapper.countByParam(orderNo, userId, payType, platform, platformNumber, status, deleteStatus);
        Set<Long> payInfoIdSet = payInfoList.stream().map(PayInfo::getId).collect(Collectors.toSet());
        Map<Long, PayPlatformVO> platformVOMap = payPlatformMapper.selectListByPayInfoIdSet(payInfoIdSet).stream()
                .map(this::payPlatform2PayPlatformVO).collect(Collectors.toMap(PayPlatformVO::getPayInfoId, v -> v));
        payInfoList.stream().map(this::payInfo2PayInfoVO).collect(Collectors.toList()).forEach(payInfoVO -> {
            payInfoVO.setPayPlatform(platformVOMap.get(payInfoVO.getId()));
            payInfoVOList.add(payInfoVO);
        });
        pageUtil.setTotal(total);
        pageUtil.setList(payInfoVOList);
        return pageUtil;
    }

    @Override
    public PayInfoVO getPayInfoVO(Long id) {
        PayInfoVO payInfoVO = new PayInfoVO();
        PayInfo payInfo = payInfoMapper.selectByPrimaryKey(id);

        if (ObjectUtils.isEmpty(payInfo) || Constants.IS_DELETE.equals(payInfo.getDeleteStatus())) {
            return payInfoVO;
        }

        payInfoVO = payInfo2PayInfoVO(payInfo);
        PayPlatform payPlatform = payPlatformMapper.selectByPayInfoId(id);

        if (ObjectUtils.isNotEmpty(payPlatform)) {
            payInfoVO.setPayPlatform(payPlatform2PayPlatformVO(payPlatform));
        }

        return payInfoVO;
    }

    private PayInfoVO payInfo2PayInfoVO(PayInfo payInfo) {
        PayInfoVO payInfoVO = new PayInfoVO();
        BeanUtils.copyProperties(payInfo, payInfoVO);
        return payInfoVO;
    }

    private PayPlatformVO payPlatform2PayPlatformVO(PayPlatform payPlatform) {
        PayPlatformVO payPlatformVO = new PayPlatformVO();
        BeanUtils.copyProperties(payPlatform, payPlatformVO);
        return payPlatformVO;
    }

}
