package com.enqbs.admin.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysMenuVO implements Serializable {

    private Integer id;

    private Integer parentId;

    private String title;

    private String path;

    private String permissionsKey;

    private Integer sort;

    private Date createTime;

    private Date updateTime;

    private List<SysMenuVO> menuList;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<SysMenuVO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<SysMenuVO> menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "SysMenuVO{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", permissionsKey='" + permissionsKey + '\'' +
                ", sort=" + sort +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", menuList=" + menuList +
                '}';
    }

}
