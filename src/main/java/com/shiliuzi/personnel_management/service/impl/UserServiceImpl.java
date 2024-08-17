package com.shiliuzi.personnel_management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.personnel_management.mapper.UserMapper;
import com.shiliuzi.personnel_management.pojo.User;
import com.shiliuzi.personnel_management.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
