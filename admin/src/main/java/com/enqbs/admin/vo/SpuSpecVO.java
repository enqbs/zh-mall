package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpuSpecVO {

    private Integer spuId;

    private List<String> pictures;

    @Override
    public String toString() {
        return "SpuSpecVO{" +
                "spuId=" + spuId +
                ", pictures=" + pictures +
                '}';
    }

}
