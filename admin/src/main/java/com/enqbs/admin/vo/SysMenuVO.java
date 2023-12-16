package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
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
