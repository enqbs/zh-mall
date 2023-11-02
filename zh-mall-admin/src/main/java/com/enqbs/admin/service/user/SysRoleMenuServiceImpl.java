package com.enqbs.admin.service.user;

import com.enqbs.generator.dao.SysRoleMenuMapper;
import com.enqbs.generator.pojo.SysRoleMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public int batchInsertRoleMenu(Integer roleId, Set<Integer> menuIdSet) {
        List<SysRoleMenu> sysRoleMenuList = buildSysRoleMenuList(roleId, menuIdSet);
        return sysRoleMenuMapper.batchInsert(sysRoleMenuList);
    }

    @Override
    public int updateRoleMenu(Integer roleId, Set<Integer> menuIdSet) {
        sysRoleMenuMapper.deleteByRoleId(roleId);
        return batchInsertRoleMenu(roleId, menuIdSet);
    }

    @Override
    public int deleteRoleMenu(Integer roleId, Set<Integer> menuIdSet) {
        List<SysRoleMenu> sysRoleMenuList = buildSysRoleMenuList(roleId, menuIdSet);
        return sysRoleMenuMapper.batchDelete(sysRoleMenuList);
    }

    private SysRoleMenu buildSysRoleMenu(Integer roleId, Integer menuId) {
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setRoleId(roleId);
        sysRoleMenu.setMenuId(menuId);
        return sysRoleMenu;
    }

    private List<SysRoleMenu> buildSysRoleMenuList(Integer roleId, Set<Integer> menuIdSet) {
        List<SysRoleMenu> sysRoleMenuList = new ArrayList<>();

        for (Integer menuId : menuIdSet) {
            SysRoleMenu sysRoleMenu = buildSysRoleMenu(roleId, menuId);
            sysRoleMenuList.add(sysRoleMenu);
        }

        return sysRoleMenuList;
    }

}
