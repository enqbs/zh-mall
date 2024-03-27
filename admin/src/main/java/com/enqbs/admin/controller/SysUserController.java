package com.enqbs.admin.controller;

import com.enqbs.admin.enums.SortEnum;
import com.enqbs.admin.form.SysRelationshipBindingForm;
import com.enqbs.admin.service.sys.UserRoleService;
import com.enqbs.admin.service.sys.UserService;
import com.enqbs.admin.vo.SysUserInfoVO;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.PageUtil;
import com.enqbs.common.util.R;
import com.enqbs.security.service.TokenService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class SysUserController {

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private TokenService tokenService;

    @GetMapping("/list")
    public R<PageUtil<SysUserInfoVO>> userListPage(@RequestParam(required = false, defaultValue = "0") Integer deleteStatus,
                                                   @RequestParam(required = false, defaultValue = "DESC") SortEnum sort,
                                                   @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                   @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        PageUtil<SysUserInfoVO> userInfoVOListPage = userService.userInfoVOListPage(deleteStatus, sort,
                pageNum <= 0 ? 1 : pageNum, pageSize <= 0 ? 10 : pageSize);
        return R.ok(userInfoVOListPage);
    }

    @GetMapping("/info")
    public R<Map<String, Object>> userInfo(@RequestHeader String token) {
        String newToken = tokenService.refreshToken(token);
        SysUserInfoVO userInfo = userService.getUserInfoVO();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userInfo", userInfo);
        resultMap.put("token", newToken);
        return R.ok(resultMap);
    }

    @PostMapping("/bind")
    @PreAuthorize("hasAuthority('SYS_USER:UPDATE')")
    public R<Void> userRoleBind(@Valid @RequestBody SysRelationshipBindingForm form) {
        int row = userRoleService.batchInsert(form.getBindId(), form.getToIdSet());

        if (row <= 0) {
            throw new ServiceException("绑定角色失败");
        }

        return R.ok("绑定角色成功");
    }

    @PutMapping("/bind")
    @PreAuthorize("hasAuthority('SYS_USER:UPDATE')")
    public R<Void> updateUserRole(@Valid @RequestBody SysRelationshipBindingForm form) {
        int row = userRoleService.batchUpdate(form.getBindId(), form.getToIdSet());

        if (row <= 0) {
            throw new ServiceException("修改角色失败");
        }

        return R.ok("修改角色成功");
    }

    @DeleteMapping("/bind")
    @PreAuthorize("hasAuthority('SYS_USER:DELETE')")
    public R<Void> deleteUserRole(@Valid @RequestBody SysRelationshipBindingForm form) {
        int row = userRoleService.batchDelete(form.getBindId(), form.getToIdSet());

        if (row <= 0) {
            throw new ServiceException("删除角色失败");
        }

        return R.ok("删除角色成功");
    }

}
