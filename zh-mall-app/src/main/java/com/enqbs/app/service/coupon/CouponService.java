package com.enqbs.app.service.coupon;

import com.enqbs.app.pojo.vo.CouponVO;
import com.enqbs.common.enums.SortEnum;
import com.enqbs.common.util.PageUtil;

import java.util.List;
import java.util.Set;

public interface CouponService {

    /*
     * 优惠券列表
     * */
    List<CouponVO> getCouponVOList(Set<Integer> couponIdSet);

    /*
    * 优惠券列表
    * */
    PageUtil<CouponVO> getCouponVOList(SortEnum sortEnum, Integer pageNum, Integer pageSize);

    /*
     * 优惠券详情
     * */
    CouponVO getCouponVO(Integer couponId);

    /*
     * 更新优惠券数量
     * */
    int update(Integer couponId, Integer quantity);

}
