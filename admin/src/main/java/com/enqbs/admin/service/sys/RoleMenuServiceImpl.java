package com.enqbs.admin.service.sys;

import com.enqbs.generator.dao.SysRoleMenuMapper;
import com.enqbs.generator.pojo.SysRoleMenu;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public int batchInsert(Integer roleId, Set<Integer> menuIdSet) {
        List<SysRoleMenu> roleMenuList = buildRoleMenuList(roleId, menuIdSet);
        return sysRoleMenuMapper.batchInsertBySysRoleMenuList(roleMenuList);
    }

    @Override
    public int batchUpdate(Integer roleId, Set<Integer> menuIdSet) {
        sysRoleMenuMapper.deleteByRoleId(roleId);
        return batchInsert(roleId, menuIdSet);
    }

    @Override
    public int batchDelete(Integer roleId, Set<Integer> menuIdSet) {
        List<SysRoleMenu> roleMenuList = buildRoleMenuList(roleId, menuIdSet);
        return sysRoleMenuMapper.batchDeleteBysSysRoleMenuList(roleMenuList);
    }

    private SysRoleMenu buildRoleMenu(Integer roleId, Integer menuId) {
        SysRoleMenu roleMenu = new SysRoleMenu();
        roleMenu.setRoleId(roleId);
        roleMenu.setMenuId(menuId);
        return roleMenu;
    }

    private List<SysRoleMenu> buildRoleMenuList(Integer roleId, Set<Integer> menuIdSet) {
        return menuIdSet.stream().map(m -> buildRoleMenu(roleId, m)).toList();
    }

}
