package com.enqbs.admin.controller;

import com.enqbs.admin.service.pay.PayInfoService;
import com.enqbs.admin.vo.PayInfoVO;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PayInfoController {

    @Resource
    private PayInfoService payInfoService;

    @GetMapping("/pay/list")
    public R<PageUtil<PayInfoVO>> payInfoList(@RequestParam(required = false) Long orderNo,
                                              @RequestParam(required = false) Integer userId,
                                              @RequestParam(required = false) String payType,
                                              @RequestParam(required = false) String platform,
                                              @RequestParam(required = false) String platformNumber,
                                              @RequestParam(required = false) Integer status,
                                              @RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        if (pageNum <= 0) {
            pageNum = 1;
        }

        if (pageSize <= 0) {
            pageSize = 10;
        }

        PageUtil<PayInfoVO> pagePayInfoVOList = payInfoService.getPayInfoVOList(orderNo, userId, payType, platform, platformNumber, status, deleteStatus, pageNum, pageSize);
        return R.ok(pagePayInfoVOList);
    }

    @GetMapping("/pay/info/{payInfoId}")
    public R<PayInfoVO> payInfoDetail(@PathVariable Long payInfoId) {
        PayInfoVO payInfoVO = payInfoService.getPayInfoVO(payInfoId);
        return R.ok(payInfoVO);
    }

}
