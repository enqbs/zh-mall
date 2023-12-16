package com.enqbs.admin.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpuOverviewVO {

    private Integer spuId;

    private List<String> pictures;

    @Override
    public String toString() {
        return "SpuOverviewVO{" +
                "spuId=" + spuId +
                ", pictures=" + pictures +
                '}';
    }

}
