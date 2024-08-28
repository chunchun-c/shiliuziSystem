package com.shiliuzi.personnel_management.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiliuzi.personnel_management.annotation.TestPermission;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.UserService;
import com.shiliuzi.personnel_management.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    //获取全部成员信息
    @TestPermission
    @GetMapping("/getAllUserInfo")
    public Result getAllUserInfo(@RequestParam(value = "pn",defaultValue = "1")Integer pn,@RequestParam(value = "size",defaultValue = "5")Integer size) {
        Result allUserInfoRet = userService.getAllUserInfo();
        List<UserVo> userVoList = (List<UserVo>) allUserInfoRet.getData();
        //分页展示
        int fromIndex = (pn - 1) * size;
        int toIndex = Math.min(fromIndex + size, userVoList.size());
        List<UserVo> pageUserList = userVoList.subList(fromIndex, toIndex);

        Page<UserVo> userVoPage = new Page<>(pn, size);
        userVoPage.setRecords(pageUserList);
        userVoPage.setTotal(userVoList.size());
        return Result.success(userVoPage);
    }
}
