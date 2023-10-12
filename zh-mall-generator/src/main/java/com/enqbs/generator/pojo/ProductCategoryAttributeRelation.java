package com.enqbs.generator.pojo;

import java.io.Serializable;

public class ProductCategoryAttributeRelation implements Serializable {

    private Integer productCategoryId;

    private Integer productCategoryAttributeId;

    private static final long serialVersionUID = 1L;

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Integer getProductCategoryAttributeId() {
        return productCategoryAttributeId;
    }

    public void setProductCategoryAttributeId(Integer productCategoryAttributeId) {
        this.productCategoryAttributeId = productCategoryAttributeId;
    }

    @Override
    public String toString() {
        return "ProductCategoryAttributeRelation{" +
                "productCategoryId=" + productCategoryId +
                ", productCategoryAttributeId=" + productCategoryAttributeId +
                '}';
    }

}
