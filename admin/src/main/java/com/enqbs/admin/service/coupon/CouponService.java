package com.enqbs.admin.service.coupon;

import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.form.CouponForm;
import com.enqbs.admin.vo.CouponVO;
import com.enqbs.common.util.PageUtil;

import java.util.Date;

public interface CouponService {

    /**
     * 优惠券列表
     *
     * @param productId 优惠券所属商品 ID
     * @param startDate 优惠券开始日期
     * @param endDate 优惠券结束日期
     * @param status 优惠券状态
     * @param deleteStatus 软删除标识
     * @param sort 排序
     * @param pageNum pn
     * @param pageSize ps
     * @return 分页信息
     */
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
