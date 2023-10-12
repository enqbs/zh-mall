package com.enqbs.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

import java.util.UUID;

public class IDUtil {

    private static final Snowflake snowflake = IdUtil.getSnowflake();

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static long getId() {
        return snowflake.nextId();
    }

}
