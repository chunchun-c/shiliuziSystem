package com.shiliuzi.personnel_management.result;

import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;

public class Result {
    //状态码
    private Integer code;

    //返回消息
    private String message;

    //返回数据
    private Object data;


    public Result() {
    }

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success() {
        return new Result(200, "success", null);
    }
    public static Result success( Object data) {
        return new Result(200, "success", data);
    }
    public static Result success(String msg, Object data) {
        return new Result(200, msg, data);
    }



    public static Result fail(String msg) {
        return new Result(400, msg,null);
    }
    public static Result fail(Integer code,String msg) {
        return new Result(code, msg, null);
    }
    public static Result fail(Integer code,String msg, Object data) {
        return new Result(code, msg, data);
    }

    public static Result fail(AppExceptionCodeMsg appExceptionCodeMsg) {
        return new Result(appExceptionCodeMsg.getCode(), appExceptionCodeMsg.getMsg(), null);
    }

    public boolean isSuccess() {
        if (code == 200) {
            return true;
        }else {
            return false;
        }

    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toString() {
        return "Result{code = " + code + ", message = " + message + ", data = " + data + "}";
    }

}
