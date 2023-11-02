package com.enqbs.admin.service.user;

import com.enqbs.generator.dao.SysUserRoleMapper;
import com.enqbs.generator.pojo.SysUserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int batchInsertUserRole(Integer userId, Set<Integer> roleIdSet) {
        List<SysUserRole> sysUserRoleList = buildSysUserRoleList(userId, roleIdSet);
        return sysUserRoleMapper.batchInsert(sysUserRoleList);
    }

    @Override
    public int updateUserRole(Integer userId, Set<Integer> roleIdSet) {
        sysUserRoleMapper.deleteByUserId(userId);
        return batchInsertUserRole(userId, roleIdSet);
    }

    @Override
    public int deleteUserRole(Integer userId, Set<Integer> roleIdSet) {
        List<SysUserRole> sysUserRoleList = buildSysUserRoleList(userId, roleIdSet);
        return sysUserRoleMapper.batchDelete(sysUserRoleList);
    }

    private SysUserRole buildSysUserRole(Integer userId, Integer roleId) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        return sysUserRole;
    }

    private List<SysUserRole> buildSysUserRoleList(Integer userId, Set<Integer> roleIdSet) {
        List<SysUserRole> sysUserRoleList = new ArrayList<>();

        for (Integer roleId : roleIdSet) {
            SysUserRole sysUserRole = buildSysUserRole(userId, roleId);
            sysUserRoleList.add(sysUserRole);
        }

        return sysUserRoleList;
    }

}
