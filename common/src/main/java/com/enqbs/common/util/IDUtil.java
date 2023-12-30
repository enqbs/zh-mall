package com.enqbs.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

import java.util.UUID;

public class IDUtil {

    private static final Snowflake SNOW_FLAKE = IdUtil.getSnowflake();

    public static long getId() {
        return SNOW_FLAKE.nextId();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
