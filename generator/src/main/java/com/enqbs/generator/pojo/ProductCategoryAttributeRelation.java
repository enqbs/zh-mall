package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class ProductCategoryAttributeRelation implements Serializable {

    private Integer productCategoryId;

    private Integer productCategoryAttributeId;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "ProductCategoryAttributeRelation{" +
                "productCategoryId=" + productCategoryId +
                ", productCategoryAttributeId=" + productCategoryAttributeId +
                '}';
    }

}
