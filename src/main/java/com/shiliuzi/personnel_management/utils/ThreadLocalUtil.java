package com.shiliuzi.personnel_management.utils;

import com.shiliuzi.personnel_management.pojo.User;

public class ThreadLocalUtil {
    // 定义两个 ThreadLocal 变量
    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> urlThreadLocal = new ThreadLocal<>();

    // 设置 User 对象
    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    // 获取 User 对象
    public static User getUser() {
        return userThreadLocal.get();
    }

    // 设置 URL 字符串
    public static void setUrl(String url) {
        urlThreadLocal.set(url);
    }

    // 获取 URL 字符串
    public static String getUrl() {
        return urlThreadLocal.get();
    }

    // 清空 ThreadLocal
    public static void clear() {
        userThreadLocal.remove();
        urlThreadLocal.remove();
    }
}
