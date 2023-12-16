package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class SpuSlideVO implements Serializable {

    private Integer spuId;

    private List<String> pictures;

    @Override
    public String toString() {
        return "SpuSlideVO{" +
                "spuId=" + spuId +
                ", pictures=" + pictures +
                '}';
    }

}
