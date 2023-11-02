package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleMapper {

    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    int deleteByUserId(Integer userId);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    int batchInsert(@Param("sysUserRoleList") List<SysUserRole> sysUserRoleList);

    int batchDelete(@Param("sysUserRoleList") List<SysUserRole> sysUserRoleList);

}
