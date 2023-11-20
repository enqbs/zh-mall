package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    SysUser selectByUsername(String username);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int countByUsername(String username);

    List<SysUser> selectListByParam(@Param("deleteStatus") Integer deleteStatus,
                                    @Param("sort") String sort,
                                    @Param("pageNum") Integer pageNum,
                                    @Param("pageSize") Integer pageSize);

    Long countByParam(@Param("deleteStatus") Integer deleteStatus);

}
