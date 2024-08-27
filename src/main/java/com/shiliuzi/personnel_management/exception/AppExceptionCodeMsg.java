package com.shiliuzi.personnel_management.exception;

import lombok.Getter;

@Getter
public enum AppExceptionCodeMsg {

    USERID_PWD_WRONG(401,"账号或密码不正确"),
    FILE_ERROR(402,"文件错误"),
    PERMISSION_ERROR(403,"权限错误"),
    NO_FIT_DATA(404,"未找到符合条件的数据"),
    TOKEN_FIND_ERROR(405,"获取当前用户的token失败"),
    TOKEN_TEST_ERROR(406,"验证token失败"),

    SERVICE_ERROR(500,"服务器异常"),
    PARAM_INVALID(1000, "无效的参数"),
    ;

    private int code ;
    private String msg ;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    AppExceptionCodeMsg(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
