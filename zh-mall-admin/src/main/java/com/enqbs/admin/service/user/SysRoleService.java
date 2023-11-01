package com.enqbs.admin.service.user;

import com.enqbs.admin.form.SysRoleForm;
import com.enqbs.admin.vo.SysRoleVO;
import com.enqbs.common.util.PageUtil;

public interface SysRoleService {

    /*
     * 角色列表
     * */
    PageUtil<SysRoleVO> getSysRoleVOList(Integer deleteStatus, Integer pageNum, Integer pageSize);

    /*
     * 角色详情
     * */
    SysRoleVO getSysRoleVO(Integer id);

    /*
     * 新增角色
     * */
    int insetSysRole(SysRoleForm form);

    /*
     * 修改角色
     * */
    int updateSysRole(Integer id, SysRoleForm form);

    /*
     * 删除角色
     * */
    int deleteSysRole(Integer id);

}
