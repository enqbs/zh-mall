package com.enqbs.admin.enums;

import lombok.Getter;

/*
* 排序枚举
* */
@Getter
public enum SortEnum {

    ASC(0, "asc"),

    DESC(1, "desc");

    private final Integer code;

    private final String sortType;

    SortEnum(Integer code, String sortType) {
        this.code = code;
        this.sortType = sortType;
    }

}
