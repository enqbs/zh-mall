package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.Coupon;

public interface CouponMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

}
