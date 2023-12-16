package com.enqbs.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.apache.hc.core5.http.HttpStatus;

/*
* 自定义 Service 异常
* */
@Getter
@Setter
public class ServiceException extends RuntimeException {

    private Integer code = HttpStatus.SC_SERVER_ERROR;

    private String msg;

    public ServiceException() {
    }

    public ServiceException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ServiceException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(Throwable cause, Integer code, String msg) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }

}
