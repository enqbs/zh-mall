package com.enqbs.admin.service.user;

import com.enqbs.admin.vo.SysRoleVO;

import java.util.List;

public interface SysRoleService {

    /*
     * 角色列表
     * */
    List<SysRoleVO> getSysRoleVOList();

}
