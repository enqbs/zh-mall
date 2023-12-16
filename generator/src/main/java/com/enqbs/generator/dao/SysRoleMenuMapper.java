package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMenuMapper {

    int deleteByPrimaryKey(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    int deleteByRoleId(Integer roleId);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    int batchInsertBySysRoleMenuList(@Param("sysRoleMenuList") List<SysRoleMenu> sysRoleMenuList);

    int batchDeleteBysSysRoleMenuList(@Param("sysRoleMenuList") List<SysRoleMenu> sysRoleMenuList);

}
