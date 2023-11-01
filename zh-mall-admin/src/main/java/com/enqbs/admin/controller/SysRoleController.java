package com.enqbs.admin.controller;

import com.enqbs.admin.form.SysRoleForm;
import com.enqbs.admin.service.user.SysMenuService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysMenuService sysMenuService;

    @GetMapping("/role/list")
    public R<PageUtil<SysRoleVO>> roleList(@RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        if (pageNum <= 0) {
            pageNum = 1;
        }

        if (pageSize <= 0) {
            pageSize = 10;
        }

        PageUtil<SysRoleVO> pageSysRoleVOList = sysRoleService.getSysRoleVOList(deleteStatus, pageNum, pageSize);
        return R.ok(pageSysRoleVOList);
    }

    @GetMapping("/role/{roleId}")
    public R<Map<String, Object>> roleDetail(@PathVariable Integer roleId) throws ExecutionException, InterruptedException {
        List<SysMenuVO> sysMenuVOList = sysMenuService.getSysMenuVOList(roleId).get();
        SysRoleVO sysRoleVO = sysRoleService.getSysRoleVO(roleId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("roleInfo", sysRoleVO);
        resultMap.put("menuList", sysMenuVOList);
        return R.ok(resultMap);
    }

    @PostMapping("/role")
    @PreAuthorize("hasAuthority('SYS_ROLE:ADD')")
    public R<Void> roleAdd(@Valid @RequestBody SysRoleForm form) {
        int row = sysRoleService.insetSysRole(form);

        if (row >= 1) {
            return R.ok("新增成功");
        } else {
            throw new ServiceException("新增失败");
        }
    }

    @PutMapping("/role/{roleId}")
    @PreAuthorize("hasAuthority('SYS_ROLE:UPDATE')")
    public R<Void> roleUpdate(@PathVariable Integer roleId, @Valid @RequestBody SysRoleForm form) {
        int row = sysRoleService.updateSysRole(roleId, form);

        if (row >= 1) {
            return R.ok("修改成功");
        } else {
            throw new ServiceException("修改失败");
        }
    }

    @DeleteMapping("/role/{roleId}")
    @PreAuthorize("hasAuthority('SYS_ROLE:DELETE')")
    public R<Void> roleDelete(@PathVariable Integer roleId) {
        int row = sysRoleService.deleteSysRole(roleId);

        if (row >= 1) {
            return R.ok("删除成功");
        } else {
            throw new ServiceException("删除失败");
        }
    }

}
