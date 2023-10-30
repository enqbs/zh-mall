package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.SysMenu;

import java.util.Set;

public interface SysMenuMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    Set<SysMenu> selectSetByUsername(String username);

}
