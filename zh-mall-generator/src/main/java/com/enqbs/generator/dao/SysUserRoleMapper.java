package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.SysUserRole;
import org.apache.ibatis.annotations.Param;

public interface SysUserRoleMapper {

    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

}
