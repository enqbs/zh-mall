package com.enqbs.file.enums;

import lombok.Getter;

@Getter
public enum DirEnum {

    SPU_IMG("spu-img/"),

    SPU_SLIDER("spu-slider/"),

    SPU_SPEC("spu-spec/"),

    SPU_OVERVIEW("spu-overview/"),

    SKU_IMG("sku-img/"),

    USER_PHOTO("user-photo/"),

    COMMENT_IMG("comment-img/");

    private final String desc;

    DirEnum(String desc) {
        this.desc = desc;
    }

}
