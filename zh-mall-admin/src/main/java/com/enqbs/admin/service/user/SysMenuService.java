package com.enqbs.admin.service.user;

import com.enqbs.admin.form.SysMenuForm;
import com.enqbs.admin.vo.SysMenuVO;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.pojo.SysMenu;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

public interface SysMenuService {

    /*
     * 获取权限列表
     * */
    Set<SysMenu> getSysMenuSet(String username);

    /*
     * 权限列表、父级列表
     * */
    List<SysMenuVO> getSysMenuVOList();

    /*
     * 权限列表
     * */
    PageUtil<SysMenuVO> getSysMenuVOList(Integer parentId, Integer roleId, Integer deleteStatus, Integer pageNum, Integer pageSize);

    /*
     * 角色 ID 获取权限列表
     * */
    Future<List<SysMenuVO>> getSysMenuVOList(Integer roleId);

    /*
     * 权限详情
     * */
    SysMenuVO getSysMenuVO(Integer id);

    /*
     * 新增权限
     * */
    int insertSysMenu(SysMenuForm form);

    /*
     * 修改权限
     * */
    int updateSysMenu(Integer id, SysMenuForm form);

    /*
     * 删除权限
     * */
    int deleteSysMenu(Integer id);

}
