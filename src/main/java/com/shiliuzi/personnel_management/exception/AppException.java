package com.shiliuzi.personnel_management.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private int code;
    private String msg;


    public AppException(AppExceptionCodeMsg appExceptionCodeMsg) {
        super();
        this.code = appExceptionCodeMsg.getCode();
        this.msg = appExceptionCodeMsg.getMsg();

    }

    public AppException(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;

    }

}
