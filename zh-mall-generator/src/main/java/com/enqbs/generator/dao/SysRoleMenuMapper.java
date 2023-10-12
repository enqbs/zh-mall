package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

public interface SysRoleMenuMapper {

    int deleteByPrimaryKey(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

}
