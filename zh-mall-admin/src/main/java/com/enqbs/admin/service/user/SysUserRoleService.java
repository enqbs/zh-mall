package com.enqbs.admin.service.user;

import java.util.Set;

public interface SysUserRoleService {

    /*
     * 用户绑定角色
     * */
    int batchInsert(Integer userId, Set<Integer> roleIdSet);

    /*
     * 更新用户角色绑定关系
     * */
    int batchUpdate(Integer userId, Set<Integer> roleIdSet);

    /*
     * 删除用户角色绑定关系
     * */
    int batchDelete(Integer userId, Set<Integer> roleIdSet);

}
