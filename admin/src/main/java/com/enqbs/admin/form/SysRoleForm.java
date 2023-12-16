package com.enqbs.admin.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SysRoleForm {

    @NotBlank(message = "角色名不能为空")
    private String name;

    @NotBlank(message = "角色标识符不能为空")
    private String roleKey;

    private Integer sort;

}
