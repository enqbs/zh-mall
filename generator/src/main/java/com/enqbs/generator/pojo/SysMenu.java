package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class SysMenu implements Serializable {

    private Integer id;

    private Integer parentId;

    private String title;

    private String path;

    private String permissionsKey;

    private Integer sort;

    private Integer deleteStatus;

    private Date createTime;

    private Date updateTime;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "SysMenu{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", permissionsKey='" + permissionsKey + '\'' +
                ", sort=" + sort +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
