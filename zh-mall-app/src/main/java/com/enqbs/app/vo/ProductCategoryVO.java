package com.enqbs.app.vo;

import java.io.Serializable;
import java.util.List;

public class ProductCategoryVO implements Serializable {

    private Integer id;

    private Integer parentId;

    private String name;

    private String icon;

    private List<ProductCategoryVO> categoryList;

    private List<ProductVO> productList;

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

    public List<ProductCategoryVO> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<ProductCategoryVO> categoryList) {
        this.categoryList = categoryList;
    }

    public List<ProductVO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductVO> productList) {
        this.productList = productList;
    }

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
