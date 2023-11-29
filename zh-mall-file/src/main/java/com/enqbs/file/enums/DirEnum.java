package com.enqbs.file.enums;

public enum DirEnum {

    SPU_IMG("spu-img/"),

    SPU_SLIDER("spu-slider/"),

    SPU_SPEC("spu-spec/"),

    SPU_OVERVIEW("spu-overview/"),

    SKU_IMG("sku-img/"),

    USER_PHOTO("user-photo/");

    private final String desc;

    public String getDesc() {
        return desc;
    }

    DirEnum(String desc) {
        this.desc = desc;
    }

}
