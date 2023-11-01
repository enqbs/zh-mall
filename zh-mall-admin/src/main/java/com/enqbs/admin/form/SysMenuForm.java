package com.enqbs.admin.form;

import javax.validation.constraints.NotBlank;

public class SysMenuForm {

    private Integer parentId;

    @NotBlank(message = "菜单标题不能为空")
    private String title;

    private String path;

    private String permissionsKey;

    private Integer sort;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPermissionsKey() {
        return permissionsKey;
    }

    public void setPermissionsKey(String permissionsKey) {
        this.permissionsKey = permissionsKey;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}
