package com.shiliuzi.personnel_management.utils;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * @description:提供全局的Validator实例，用于对象验证
 * @author: chun
 **/

public class ValidationUtil {

    public static Validator getValidator() {
        return validator;
    }

    static Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

}