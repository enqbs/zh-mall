package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.PayInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);

    PayInfo selectByOrderNo(Long orderNo);

    List<PayInfo> selectListParam(@Param("orderNo") Long orderNo,
                                  @Param("userId") Integer userId,
                                  @Param("payType") String payType,
                                  @Param("platform") String platform,
                                  @Param("platformNumber") String platformNumber,
                                  @Param("status") Integer status,
                                  @Param("deleteStatus") Integer deleteStatus,
                                  @Param("sort") String sort,
                                  @Param("pageNum") Integer pageNum,
                                  @Param("pageSize") Integer pageSize);

    Long countByParam(@Param("orderNo") Long orderNo,
                      @Param("userId") Integer userId,
                      @Param("payType") String payType,
                      @Param("platform") String platform,
                      @Param("platformNumber") String platformNumber,
                      @Param("status") Integer status,
                      @Param("deleteStatus") Integer deleteStatus);

}
