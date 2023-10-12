package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.UserCoupon;
import org.apache.ibatis.annotations.Param;

public interface UserCouponMapper {

    int deleteByPrimaryKey(@Param("couponId") Integer couponId, @Param("userId") Integer userId);

    int insert(UserCoupon record);

    int insertSelective(UserCoupon record);

    UserCoupon selectByPrimaryKey(@Param("couponId") Integer couponId, @Param("userId") Integer userId);

    int updateByPrimaryKeySelective(UserCoupon record);

    int updateByPrimaryKey(UserCoupon record);

}
