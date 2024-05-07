package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.Coupon;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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

    List<Coupon> selectListByIdSet(@Param("idSet") Set<Integer> idSet);

    List<Coupon> selectListByParam(@Param("productId") Integer productId,
                                   @Param("startDate") Date startDate,
                                   @Param("endDate") Date endDate,
                                   @Param("status") Integer status,
                                   @Param("deleteStatus") Integer deleteStatus,
                                   @Param("sort") String sort,
                                   @Param("pageNum") Integer pageNum,
                                   @Param("pageSize") Integer pageSize);

    Long countByParam(@Param("productId") Integer productId,
                      @Param("startDate") Date startDate,
                      @Param("endDate") Date endDate,
                      @Param("status") Integer status,
                      @Param("deleteStatus") Integer deleteStatus);

}
