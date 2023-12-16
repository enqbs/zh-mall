package com.enqbs.generator.dao;

import com.enqbs.generator.pojo.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SysMenuMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    Set<SysMenu> selectSetByUsername(String username);

    List<SysMenu> selectListByRoot();

    List<SysMenu> selectListByRoleId(Integer roleId);

    List<SysMenu> selectListByParam(@Param("parentId") Integer parentId,
                                    @Param("roleId") Integer roleId,
                                    @Param("deleteStatus") Integer deleteStatus,
                                    @Param("pageNum") Integer pageNum,
                                    @Param("pageSize") Integer pageSize);

    Long countByParam(@Param("parentId") Integer parentId,
                      @Param("roleId") Integer roleId,
                      @Param("deleteStatus") Integer deleteStatus);

}
