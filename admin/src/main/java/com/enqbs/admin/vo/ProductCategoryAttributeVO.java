package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryAttributeVO {

    private Integer id;

    private String name;

    private Integer deleteStatus;

    @Override
    public String toString() {
        return "ProductCategoryAttributeVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deleteStatus=" + deleteStatus +
                '}';
    }

}
