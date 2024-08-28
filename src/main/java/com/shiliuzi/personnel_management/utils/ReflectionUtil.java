package com.shiliuzi.personnel_management.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 反射工具类
 * @author: chun
 **/

@Slf4j
public class ReflectionUtil {
    /**
     * 将对象的字段及其值转换为 Map。
     *
     * @param obj 需要转换的对象
     * @return 包含对象字段及其值的 Map
     */
    public static <T>Map<String, Object> toMap(T obj) {
        if (obj == null) {
            throw new IllegalArgumentException("对象不能为 null");
        }

        Map<String, Object> fieldMap = new HashMap<>();

        // 获取对象的所有字段
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                fieldMap.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                log.error("转为map出错");
                e.printStackTrace();
            }
        }

        return fieldMap;
    }
}
