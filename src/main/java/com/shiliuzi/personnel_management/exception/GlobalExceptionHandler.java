package com.shiliuzi.personnel_management.exception;

import com.shiliuzi.personnel_management.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
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
        } else if (e instanceof MethodArgumentNotValidException) {
            //是否为参数异常
            List<FieldError> fieldErrors =((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
            String message = fieldErrors.stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return Result.fail(AppExceptionCodeMsg.PARAM_INVALID, message);
        } else {
            return Result.fail(AppExceptionCodeMsg.SERVICE_ERROR);
        }

    }




}
