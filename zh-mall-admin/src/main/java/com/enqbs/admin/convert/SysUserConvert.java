package com.enqbs.admin.convert;

import com.enqbs.admin.form.SysMenuForm;
import com.enqbs.admin.form.SysRoleForm;
import com.enqbs.admin.vo.SysMenuVO;
import com.enqbs.admin.vo.SysRoleVO;
import com.enqbs.admin.vo.SysUserInfoVO;
import com.enqbs.generator.pojo.SysMenu;
import com.enqbs.generator.pojo.SysRole;
import com.enqbs.generator.pojo.SysUser;
import com.enqbs.security.pojo.LoginUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SysUserConvert {

    SysMenu sysMenuForm2SysMenu(SysMenuForm form);

    SysMenuVO sysMenu2SysMenuVO(SysMenu sysMenu);

    SysRole sysRoleForm2SysRole(SysRoleForm form);

    SysRoleVO sysRole2SysRoleVO(SysRole sysRole);

    SysUserInfoVO sysUserInfo2SysUserInfoVO(SysUser sysUser);

    @Mapping(source = "userId", target = "id")
    SysUserInfoVO loginUser2SysUserInfoVO(LoginUser loginUser);

}
