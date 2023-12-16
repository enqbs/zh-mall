package com.enqbs.app.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ProductCategoryVO implements Serializable {

    private Integer id;

    private Integer parentId;

    private String name;

    private String icon;

    private List<ProductCategoryVO> categoryList;

    private List<ProductVO> productList;

    @Override
    public String toString() {
        return "ProductCategoryVO{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", categoryList=" + categoryList +
                ", productList=" + productList +
                '}';
    }

}
