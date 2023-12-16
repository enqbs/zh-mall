package com.enqbs.app.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SkuParamVO implements Serializable {

    private String paramKey;

    private String paramValue;

    @Override
    public String toString() {
        return "SkuParamVO{" +
                "paramKey='" + paramKey + '\'' +
                ", paramValue='" + paramValue + '\'' +
                '}';
    }

}
