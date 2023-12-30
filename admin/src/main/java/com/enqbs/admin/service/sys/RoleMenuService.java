package com.enqbs.admin.service.sys;

import java.util.Set;

public interface RoleMenuService {

    /*
     * 角色绑定菜单（权限）
     * */
    int batchInsert(Integer roleId, Set<Integer> menuIdSet);

    /*
     * 更新角色菜单绑定关系
     * */
    int batchUpdate(Integer roleId, Set<Integer> menuIdSet);

    /*
     * 删除角色菜单绑定关系
     * */
    int batchDelete(Integer roleId, Set<Integer> menuIdSet);

}
