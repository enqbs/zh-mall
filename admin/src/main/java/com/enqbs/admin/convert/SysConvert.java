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
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SysConvert {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "deleteStatus", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updateTime", ignore = true)
    })
    SysMenu form2Menu(SysMenuForm form);

    @Mapping(target = "menuList", ignore = true)
    SysMenuVO menu2MenuVO(SysMenu menu);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "deleteStatus", ignore = true),
            @Mapping(target = "createTime", ignore = true),
            @Mapping(target = "updateTime", ignore = true)
    })
    SysRole form2Role(SysRoleForm form);

    SysRoleVO role2RoleVO(SysRole role);

    SysUserInfoVO user2UserInfoVO(SysUser user);

    @Mapping(source = "userId", target = "id")
    SysUserInfoVO loginUser2UserInfoVO(LoginUser loginUser);

}
