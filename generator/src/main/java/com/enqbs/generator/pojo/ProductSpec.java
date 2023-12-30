package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class ProductSpec implements Serializable {

    private Integer id;

    private Integer productId;

    private String picture;

    private Integer deleteStatus;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "ProductSpec{" +
                "id=" + id +
                ", productId=" + productId +
                ", picture='" + picture + '\'' +
                ", deleteStatus=" + deleteStatus +
                '}';
    }

}
