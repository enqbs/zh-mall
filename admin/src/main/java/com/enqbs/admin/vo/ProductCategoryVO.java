package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductCategoryVO implements Serializable {

    private Integer id;

    private Integer parentId;

    private String name;

    private String icon;

    private Integer sort;

    private Integer naviStatus;

    private Integer deleteStatus;

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
