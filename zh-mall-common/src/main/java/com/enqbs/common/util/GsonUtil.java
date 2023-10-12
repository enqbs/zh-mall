package com.enqbs.common.util;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GsonUtil {

    private static final Gson GSON = new Gson();

    public static String obj2Json(Object obj) {
        return GSON.toJson(obj);
    }

    public static <T> T json2Obj(String jsonStr, Class<T> clazz) {
        return GSON.fromJson(jsonStr, clazz);
    }

    public static <T> List<T> json2ArrayList(String jsonStr, Class<T[]> clazzArray) {
        T[] arrayOfT = GSON.fromJson(jsonStr, clazzArray);
        return new ArrayList<T>(Arrays.asList(arrayOfT));
    }

}
