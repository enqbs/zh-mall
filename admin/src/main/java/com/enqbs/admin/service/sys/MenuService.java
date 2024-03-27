package com.enqbs.admin.service.sys;

import com.enqbs.admin.form.SysMenuForm;
import com.enqbs.admin.vo.SysMenuVO;
import com.enqbs.common.util.PageUtil;
import com.enqbs.generator.pojo.SysMenu;

import java.util.List;
import java.util.Set;

public interface MenuService {

    /*
     * 权限列表
     * */
    PageUtil<SysMenuVO> menuVOListPage(Integer parentId, Integer roleId, Integer deleteStatus, Integer pageNum, Integer pageSize);

    /*
     * 获取权限列表
     * */
    Set<SysMenu> getMenuSet(String username);

    /*
     * 权限列表、父级列表
     * */
    List<SysMenuVO> getMenuVOList();

    /*
     * 角色 ID 获取权限列表
     * */
    List<SysMenuVO> getMenuVOList(Integer roleId);

    /*
     * 权限详情
     * */
    SysMenuVO getMenuVO(Integer id);

    /*
     * 新增权限
     * */
    int insert(SysMenuForm form);

    /*
     * 修改权限
     * */
    int update(Integer id, SysMenuForm form);

    /*
     * 删除权限
     * */
    int delete(Integer id);

}
