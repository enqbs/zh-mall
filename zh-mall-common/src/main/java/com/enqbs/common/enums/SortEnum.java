package com.enqbs.common.enums;

/*
* 排序枚举
* */
public enum SortEnum {

    ASC(0, "asc"),

    DESC(1, "desc");

    private final Integer code;

    private final String sortType;

    public Integer getCode() {
        return code;
    }

    public String getSortType() {
        return sortType;
    }

    SortEnum(Integer code, String sortType) {
        this.code = code;
        this.sortType = sortType;
    }

}
