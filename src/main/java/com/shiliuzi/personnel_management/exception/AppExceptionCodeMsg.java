package com.shiliuzi.personnel_management.exception;

import lombok.Getter;

@Getter
public enum AppExceptionCodeMsg {

    USERID_PWD_WRONG(400, "账号或密码不正确"),
    INVALID_NAME_FORMAT(400, "姓名格式错误"),
    INVALID_STUDENT_ID_FORMAT(400, "学号格式错误"),
    INVALID_PASSWORD_FORMAT(400, "密码格式错误"),
    GRADE_ERROR(400, "年级信息错误"),
    ACCOUNT_ERROR(400, "账号或密码错误"),
    EXCEL_DOWNLOAD_ERROR(400, "excel文件写入错误"),
    EXCEL_EXPORT_ERROR(400, "excel文件导出错误"),
    EXCEL_ATTRIBUTE_ERROR(400, "excel设置属性错误"),
    REVOKE_INFO_NOT_FOUND(400, "撤销奖惩记录时未找到该记录"),
    APPLY_REVOKE_ERROR(400, "同意申请撤销失败"),

    FILE_ERROR(402, "文件读取错误"),


    NO_FIT_DATA(404, "未找到符合条件的数据,请检查条件"),

    PERMISSION_ERROR(403, "权限错误"),
    TOKEN_FIND_ERROR(401, "获取当前用户的token失败"),
    TOKEN_TEST_ERROR(401, "验证token失败"),

    SERVICE_ERROR(500, "服务器异常"),
    PARAM_INVALID(1000, "无效的参数"),

    ;

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    AppExceptionCodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
