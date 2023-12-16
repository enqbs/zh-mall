package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductCategoryAttribute implements Serializable {

    private Integer id;

    private String name;

    private Integer deleteStatus;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "ProductCategoryAttribute{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deleteStatus=" + deleteStatus +
                '}';
    }

}
