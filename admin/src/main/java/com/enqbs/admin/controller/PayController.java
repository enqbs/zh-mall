package com.enqbs.admin.controller;

import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.service.pay.PayInfoService;
import com.enqbs.admin.vo.PayInfoVO;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Resource
    private PayInfoService payInfoService;

    @GetMapping("/list")
    public R<PageUtil<PayInfoVO>> payInfoPage(@RequestParam(required = false) Long orderNo,
                                              @RequestParam(required = false) Integer userId,
                                              @RequestParam(required = false) String payType,
                                              @RequestParam(required = false) String platform,
                                              @RequestParam(required = false) String platformNumber,
                                              @RequestParam(required = false) Integer status,
                                              @RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                              @RequestParam(required = false, defaultValue = "DESC") SortEnum sort,
                                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageUtil<PayInfoVO> payInfoVOPage = payInfoService.payInfoVOPage(orderNo, userId, payType, platform, platformNumber,
                status, deleteStatus, sort, pageNum <= 0 ? 1 : pageNum, pageSize <= 0 ? 10 : pageSize);
        return R.ok(payInfoVOPage);
    }

    @GetMapping("/info/{payInfoId}")
    public R<PayInfoVO> payInfoDetail(@PathVariable Long payInfoId) {
        PayInfoVO payInfo = payInfoService.getPayInfoVO(payInfoId);
        return R.ok(payInfo);
    }

}
