package com.enqbs.admin.controller;

import com.enqbs.admin.form.SysRelationshipBindingForm;
import com.enqbs.admin.form.SysRoleForm;
import com.enqbs.admin.service.user.SysMenuService;
import com.enqbs.admin.service.user.SysRoleMenuService;
import com.enqbs.admin.service.user.SysRoleService;
import com.enqbs.admin.vo.SysMenuVO;
import com.enqbs.admin.vo.SysRoleVO;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @GetMapping("/list")
    public R<PageUtil<SysRoleVO>> roleList(@RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        if (pageNum <= 0) {
            pageNum = 1;
        }

        if (pageSize <= 0) {
            pageSize = 10;
        }

        PageUtil<SysRoleVO> pageSysRoleList = sysRoleService.getSysRoleVOList(deleteStatus, pageNum, pageSize);
        return R.ok(pageSysRoleList);
    }

    @GetMapping("/{roleId}")
    public R<Map<String, Object>> roleDetail(@PathVariable Integer roleId) throws ExecutionException, InterruptedException {
        List<SysMenuVO> sysMenuList = sysMenuService.getSysMenuVOList(roleId).get();
        SysRoleVO sysRoleInfo = sysRoleService.getSysRoleVO(roleId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("roleInfo", sysRoleInfo);
        resultMap.put("menuList", sysMenuList);
        return R.ok(resultMap);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SYS_ROLE:ADD')")
    public R<Void> addRole(@Valid @RequestBody SysRoleForm form) {
        int row = sysRoleService.insetSysRole(form);

        if (row <= 0) {
            throw new ServiceException("角色新增失败");
        }

        return R.ok("角色新增成功");
    }

    @PutMapping("/{roleId}")
    @PreAuthorize("hasAuthority('SYS_ROLE:UPDATE')")
    public R<Void> updateRole(@PathVariable Integer roleId, @Valid @RequestBody SysRoleForm form) {
        int row = sysRoleService.updateSysRole(roleId, form);

        if (row <= 0) {
            throw new ServiceException("角色修改失败");
        }

        return R.ok("角色修改成功");
    }

    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('SYS_ROLE:DELETE')")
    public R<Void> deleteRole(@PathVariable Integer roleId) {
        int row = sysRoleService.deleteSysRole(roleId);

        if (row <= 0) {
            throw new ServiceException("角色删除失败");
        }

        return R.ok("角色删除成功");
    }

    @PostMapping("/bind")
    @PreAuthorize("hasAuthority('SYS_ROLE:UPDATE')")
    public R<Void> roleMenuBind(@Valid @RequestBody SysRelationshipBindingForm form) {
        int row = sysRoleMenuService.batchInsertRoleMenu(form.getBindId(), form.getToIdSet());

        if (row <= 0) {
            throw new ServiceException("绑定权限失败");
        }

        return R.ok("绑定权限成功");
    }

    @PutMapping("/bind")
    @PreAuthorize("hasAuthority('SYS_ROLE:UPDATE')")
    public R<Void> updateRoleMenu(@Valid @RequestBody SysRelationshipBindingForm form) {
        int row = sysRoleMenuService.updateRoleMenu(form.getBindId(), form.getToIdSet());

        if (row <= 0) {
            throw new ServiceException("修改权限失败");
        }

        return R.ok("修改权限成功");
    }

    @DeleteMapping("/bind")
    @PreAuthorize("hasAuthority('SYS_ROLE:DELETE')")
    public R<Void> deleteRoleMenu(@Valid @RequestBody SysRelationshipBindingForm form) {
        int row = sysRoleMenuService.deleteRoleMenu(form.getBindId(), form.getToIdSet());

        if (row <= 0) {
            throw new ServiceException("删除权限失败");
        }

        return R.ok("删除权限成功");
    }

}
