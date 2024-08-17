package com.shiliuzi.personnel_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.personnel_management.pojo.Group;
import com.shiliuzi.personnel_management.result.Result;

public interface GroupService extends IService<Group> {
    Result getGroupIdByUserId(Integer UserId);
}
