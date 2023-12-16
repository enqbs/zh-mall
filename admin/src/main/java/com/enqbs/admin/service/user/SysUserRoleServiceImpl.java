package com.enqbs.admin.service.user;

import com.enqbs.generator.dao.SysUserRoleMapper;
import com.enqbs.generator.pojo.SysUserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int batchInsert(Integer userId, Set<Integer> roleIdSet) {
        List<SysUserRole> sysUserRoleList = buildSysUserRoleList(userId, roleIdSet);
        return sysUserRoleMapper.batchInsertBySysUserRoleList(sysUserRoleList);
    }

    @Override
    public int batchUpdate(Integer userId, Set<Integer> roleIdSet) {
        sysUserRoleMapper.deleteByUserId(userId);
        return batchInsert(userId, roleIdSet);
    }

    @Override
    public int batchDelete(Integer userId, Set<Integer> roleIdSet) {
        List<SysUserRole> sysUserRoleList = buildSysUserRoleList(userId, roleIdSet);
        return sysUserRoleMapper.batchDeleteBySysUserRoleList(sysUserRoleList);
    }

    private SysUserRole buildSysUserRole(Integer userId, Integer roleId) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        return sysUserRole;
    }

    private List<SysUserRole> buildSysUserRoleList(Integer userId, Set<Integer> roleIdSet) {
        return roleIdSet.stream().map(e -> buildSysUserRole(userId, e)).collect(Collectors.toList());
    }

}
