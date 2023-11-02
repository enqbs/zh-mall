package com.enqbs.admin.service.user;

import java.util.Set;

public interface SysRoleMenuService {

    /*
     * 角色绑定菜单（权限）
     * */
    int batchInsertRoleMenu(Integer roleId, Set<Integer> menuIdSet);

    /*
     * 更新角色菜单绑定关系
     * */
    int updateRoleMenu(Integer roleId, Set<Integer> menuIdSet);

    /*
     * 删除角色菜单绑定关系
     * */
    int deleteRoleMenu(Integer roleId, Set<Integer> menuIdSet);

}
