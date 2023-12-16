package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductOverview implements Serializable {

    private Integer id;

    private Integer productId;

    private String picture;

    private Integer deleteStatus;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "ProductOverview{" +
                "id=" + id +
                ", productId=" + productId +
                ", picture='" + picture + '\'' +
                ", deleteStatus=" + deleteStatus +
                '}';
    }

}