package com.enqbs.admin.form;

import javax.validation.constraints.NotBlank;

public class SysRoleForm {

    @NotBlank(message = "角色名不能为空")
    private String name;

    @NotBlank(message = "角色标识符不能为空")
    private String roleKey;

    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}
