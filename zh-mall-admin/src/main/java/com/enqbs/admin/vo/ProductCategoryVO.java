package com.enqbs.admin.vo;

import java.io.Serializable;

public class ProductCategoryVO implements Serializable {

    private Integer id;

    private Integer parentId;

    private String name;

    private String icon;

    private Integer sort;

    private Integer naviStatus;

    private Integer deleteStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public String toString() {
        return "ProductCategoryVO{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", sort=" + sort +
                ", naviStatus=" + naviStatus +
                ", deleteStatus=" + deleteStatus +
                '}';
    }

}
