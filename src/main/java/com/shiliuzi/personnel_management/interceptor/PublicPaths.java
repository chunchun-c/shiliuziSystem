package com.shiliuzi.personnel_management.interceptor;

public enum PublicPaths {
    MAIN("/main"),

    ;

    private final String path;

    PublicPaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
