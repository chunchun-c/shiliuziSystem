package com.shiliuzi.personnel_management.exception;

import com.shiliuzi.personnel_management.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
//全局异常处理
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Result exceptionHandler(Exception e) {

        //判断是否为自定义异常
        if (e instanceof AppException) {
            AppException appException = (AppException) e;
            return Result.fail(appException.getCode(), appException.getMsg());
        } else {
            return Result.fail(AppExceptionCodeMsg.SERVICE_ERROR);
        }

    }


    //暂时不启用，后续用到@validate时启用
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        try {
            List<ObjectError> errors = ex.getBindingResult().getAllErrors();
            String message = errors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
            log.error("param illegal: {}", message);
            return Result.fail(AppExceptionCodeMsg.PARAM_INVALID, message);
        } catch (Exception e) {
            return Result.fail(AppExceptionCodeMsg.SERVICE_ERROR);
        }
    }

}
