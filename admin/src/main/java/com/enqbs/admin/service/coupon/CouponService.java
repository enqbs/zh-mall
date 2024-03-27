package com.enqbs.admin.service.coupon;

import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.form.CouponForm;
import com.enqbs.admin.vo.CouponVO;
import com.enqbs.common.util.PageUtil;

import java.util.Date;

public interface CouponService {

    /*
     * 优惠券列表
     * */
    PageUtil<CouponVO> couponVOListPage(Integer productId, Date startDate, Date endDate, Integer status,
                                        Integer deleteStatus, SortEnum sort, Integer pageNum, Integer pageSize);

    /*
     * 优惠券信息
     * */
    CouponVO getCouponVO(Integer couponId);

    /*
     * 新增优惠券
     * */
    int insert(CouponForm form);

    /*
     * 修改优惠券
     * */
    int update(Integer couponId, CouponForm form);

    /*
     * 删除优惠券
     * */
    int delete(Integer couponId);

}
