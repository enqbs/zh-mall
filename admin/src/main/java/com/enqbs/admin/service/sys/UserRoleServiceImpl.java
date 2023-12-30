package com.enqbs.admin.service.sys;

import com.enqbs.generator.dao.SysUserRoleMapper;
import com.enqbs.generator.pojo.SysUserRole;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int batchInsert(Integer userId, Set<Integer> roleIdSet) {
        List<SysUserRole> userRoleList = buildUserRoleList(userId, roleIdSet);
        return sysUserRoleMapper.batchInsertBySysUserRoleList(userRoleList);
    }

    @Override
    public int batchUpdate(Integer userId, Set<Integer> roleIdSet) {
        sysUserRoleMapper.deleteByUserId(userId);
        return batchInsert(userId, roleIdSet);
    }

    @Override
    public int batchDelete(Integer userId, Set<Integer> roleIdSet) {
        List<SysUserRole> userRoleList = buildUserRoleList(userId, roleIdSet);
        return sysUserRoleMapper.batchDeleteBySysUserRoleList(userRoleList);
    }

    private SysUserRole buildUserRole(Integer userId, Integer roleId) {
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return userRole;
    }

    private List<SysUserRole> buildUserRoleList(Integer userId, Set<Integer> roleIdSet) {
        return roleIdSet.stream().map(r -> buildUserRole(userId, r)).toList();
    }

}
