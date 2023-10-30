package com.enqbs.admin.service.user;

import com.enqbs.generator.pojo.SysMenu;

import java.util.Set;

public interface SysMenuService {

    /*
    * 权限列表
    * */
    Set<SysMenu> getSysMenuSet(String username);

}
