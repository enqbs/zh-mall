package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class SysRoleVO implements Serializable {

    private Integer id;

    private String name;

    private String roleKey;

    private Integer sort;

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        return "SysRoleVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roleKey='" + roleKey + '\'' +
                ", sort=" + sort +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
