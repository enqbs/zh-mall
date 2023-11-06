package com.enqbs.admin.form;

public class ProductCategoryForm {

    private Integer parentId;

    private String name;

    private String icon;

    private Integer sort;

    private Integer naviStatus;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getNaviStatus() {
        return naviStatus;
    }

    public void setNaviStatus(Integer naviStatus) {
        this.naviStatus = naviStatus;
    }

}
