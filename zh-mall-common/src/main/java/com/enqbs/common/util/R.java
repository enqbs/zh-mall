package com.enqbs.common.util;

import com.enqbs.common.constant.Constants;
import org.apache.hc.core5.http.HttpStatus;

public class R<T> {

    private int code;

    private String msg;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private static <T> R<T> restResult(int code, String msg, T data) {
        R<T> result = new R<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> R<T> ok() {
        return restResult(HttpStatus.SC_SUCCESS, Constants.SUCCESS, null);
    }

    public static <T> R<T> ok(String msg) {
        return restResult(HttpStatus.SC_SUCCESS, msg, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(HttpStatus.SC_SUCCESS, Constants.SUCCESS, data);
    }

    public static <T> R<T> ok(String msg, T data) {
        return restResult(HttpStatus.SC_SUCCESS, msg, data);
    }

    public static <T> R<T> error() {
        return restResult(HttpStatus.SC_SERVER_ERROR, Constants.SERVER_ERROR, null);
    }

    public static <T> R<T> error(String msg) {
        return restResult(HttpStatus.SC_SERVER_ERROR, msg, null);
    }

    public static <T> R<T> error(int code, String msg) {
        return restResult(code, msg, null);
    }

}
