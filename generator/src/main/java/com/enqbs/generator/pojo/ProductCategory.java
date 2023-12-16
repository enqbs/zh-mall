package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductCategory implements Serializable {

    private Integer id;

    private Integer parentId;

    private String name;

    private String icon;

    private Integer sort;

    private Integer homeStatus;

    private Integer naviStatus;

    private Integer deleteStatus;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", sort=" + sort +
                ", homeStatus=" + homeStatus +
                ", naviStatus=" + naviStatus +
                ", deleteStatus=" + deleteStatus +
                '}';
    }

}
