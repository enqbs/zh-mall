package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.UserCoupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCouponMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserCoupon record);

    int insertSelective(UserCoupon record);

    UserCoupon selectByPrimaryKey(Integer id);

    UserCoupon selectByCouponIdAndUserId(@Param("couponId") Integer couponId, @Param("userId") Integer userId);

    int updateStatusByCouponIdAndUserId(@Param("couponId") Integer couponId, @Param("userId") Integer userId, @Param("status") Integer status);

    int updateByPrimaryKeySelective(UserCoupon record);

    int updateByPrimaryKey(UserCoupon record);

    List<UserCoupon> selectListByParam(@Param("userId") Integer userId,
                                       @Param("status") Integer status,
                                       @Param("deleteStatus") Integer deleteStatus,
                                       @Param("sort") String sort,
                                       @Param("pageNum") Integer pageNum,
                                       @Param("pageSize") Integer pageSize);

    Long countByParam(@Param("userId") Integer userId,
                      @Param("status") Integer status,
                      @Param("deleteStatus") Integer deleteStatus);

    Integer existByCouponIdAndUserId(@Param("couponId") Integer couponId, @Param("userId") Integer userId);

}
