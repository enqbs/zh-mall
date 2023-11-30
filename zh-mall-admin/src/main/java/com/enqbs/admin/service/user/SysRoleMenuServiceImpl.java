package com.enqbs.admin.service.user;

import com.enqbs.generator.dao.SysRoleMenuMapper;
import com.enqbs.generator.pojo.SysRoleMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public int batchInsert(Integer roleId, Set<Integer> menuIdSet) {
        List<SysRoleMenu> sysRoleMenuList = buildSysRoleMenuList(roleId, menuIdSet);
        return sysRoleMenuMapper.batchInsertBySysRoleMenuList(sysRoleMenuList);
    }

    @Override
    public int batchUpdate(Integer roleId, Set<Integer> menuIdSet) {
        sysRoleMenuMapper.deleteByRoleId(roleId);
        return batchInsert(roleId, menuIdSet);
    }

    @Override
    public int batchDelete(Integer roleId, Set<Integer> menuIdSet) {
        List<SysRoleMenu> sysRoleMenuList = buildSysRoleMenuList(roleId, menuIdSet);
        return sysRoleMenuMapper.batchDeleteBysSysRoleMenuList(sysRoleMenuList);
    }

    private SysRoleMenu buildSysRoleMenu(Integer roleId, Integer menuId) {
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setRoleId(roleId);
        sysRoleMenu.setMenuId(menuId);
        return sysRoleMenu;
    }

    private List<SysRoleMenu> buildSysRoleMenuList(Integer roleId, Set<Integer> menuIdSet) {
        return menuIdSet.stream().map(e -> buildSysRoleMenu(roleId, e)).collect(Collectors.toList());
    }

}
