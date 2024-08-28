package com.shiliuzi.personnel_management.controller;

import com.shiliuzi.personnel_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    UserService userService;



}
