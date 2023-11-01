package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> selectListByParam(@Param("deleteStatus") Integer deleteStatus,
                                    @Param("pageNum") Integer pageNum,
                                    @Param("pageSize") Integer pageSize);

    Long countByParam(Integer deleteStatus);

}
