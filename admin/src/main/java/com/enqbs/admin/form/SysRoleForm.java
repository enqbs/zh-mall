package com.enqbs.admin.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysRoleForm {

    @NotBlank(message = "角色名不能为空")
    private String name;

    @NotBlank(message = "角色标识符不能为空")
    private String roleKey;

    private Integer sort;

}
