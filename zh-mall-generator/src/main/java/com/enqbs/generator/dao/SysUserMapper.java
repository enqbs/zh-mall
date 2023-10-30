package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.SysUser;

public interface SysUserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    SysUser selectByUsername(String username);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int countByUsername(String username);

}
