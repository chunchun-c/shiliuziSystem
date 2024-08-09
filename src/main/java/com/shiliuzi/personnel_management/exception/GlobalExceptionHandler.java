package com.shiliuzi.personnel_management.exception;

import com.shiliuzi.personnel_management.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
//全局异常处理
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Result exceptionHandler(Exception e){

        //判断是否为自定义异常
        if (e instanceof AppException){
            AppException appException=(AppException) e;
            return Result.fail(appException.getCode(),appException.getMsg());
        }else {
            return Result.fail(500,"服务器端异常");
        }

    }
}
