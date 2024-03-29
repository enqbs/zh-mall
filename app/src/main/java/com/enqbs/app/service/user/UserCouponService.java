package com.enqbs.app.service.user;

import com.enqbs.app.pojo.vo.UserCouponVO;
import com.enqbs.app.enums.SortEnum;
import com.enqbs.common.util.PageUtil;

import java.util.List;

public interface UserCouponService {

    /*
     * 用户优惠券列表
     * */
    PageUtil<UserCouponVO> couponVOListPage(Integer status, SortEnum sort, Integer pageNum, Integer pageSize);

    /*
     * 用户有效优惠券列表
     * */
    List<UserCouponVO> getCouponVOList();

    /*
     * 用户优惠券详情
     * */
    UserCouponVO getCouponVO(Integer couponId);

    /*
     * 用户新增优惠券
     * */
    void add(Integer couponId);

    /*
     * 保存用户优惠券信息
     * */
    void insert(Integer couponId, Integer userId, Integer quantity);

    /*
     * 扣除用户优惠券
     * */
    void deduct(Long orderNo, Integer couponId);

    /*
     * 回滚用户优惠券
     * */
    void rollback(Long orderNo, Integer couponId);

    /*
     * 用户删除优惠券
     * */
    int delete(Integer couponId);

}
