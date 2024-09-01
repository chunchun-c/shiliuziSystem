package com.shiliuzi.personnel_management;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.shiliuzi.personnel_management.mapper")
@SpringBootApplication
public class PersonnelManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonnelManagementApplication.class, args);
    }

}
