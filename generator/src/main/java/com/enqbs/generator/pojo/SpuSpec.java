package com.enqbs.generator.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SpuSpec implements Serializable {

    private Integer spuId;

    private String pictures;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "SpuSpec{" +
                "spuId=" + spuId +
                ", pictures='" + pictures + '\'' +
                '}';
    }

}
