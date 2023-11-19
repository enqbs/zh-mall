package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.Coupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface CouponMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

    int updateByDeductQuantity(@Param("couponId") Integer couponId, @Param("quantity") Integer quantity);

    List<Coupon> selectListByCouponIdSet(@Param("idSet") Set<Integer> idSet);

}
