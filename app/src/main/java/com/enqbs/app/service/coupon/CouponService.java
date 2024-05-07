package com.enqbs.app.service.coupon;

import com.enqbs.app.pojo.vo.CouponVO;
import com.enqbs.app.enums.SortEnum;
import com.enqbs.common.util.PageUtil;

import java.util.List;
import java.util.Set;

public interface CouponService {

    /**
     * 优惠券列表（分页）
     *
     * @param sort 排序
     * @param pageNum pn
     * @param pageSize ps
     * @return 分页信息
     */
    PageUtil<CouponVO> couponVOListPage(SortEnum sort, Integer pageNum, Integer pageSize);

    /**
     * 优惠券列表
     *
     * @param couponIdSet 优惠券 ID 集合
     * @return 优惠券列表
     */
    List<CouponVO> getCouponVOList(Set<Integer> couponIdSet);

    /**
     * 优惠券详情
     *
     * @param couponId 优惠券 ID
     * @return 优惠券详情
     */
    CouponVO getCouponVO(Integer couponId);

    /**
     * 更新优惠券数量
     *
     * @param couponId 优惠券 ID
     * @param quantity 数量
     * @return row
     */
    int update(Integer couponId, Integer quantity);

}
