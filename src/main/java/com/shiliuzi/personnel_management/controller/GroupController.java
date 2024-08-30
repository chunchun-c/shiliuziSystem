package com.shiliuzi.personnel_management.controller;

import com.shiliuzi.personnel_management.pojo.Group;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    //获取所有组别
    @GetMapping("/getAllGroup")
    public Result getAllGroup() {
        List<Group> groupList = groupService.list();
        return Result.success(groupList);
    }
}
