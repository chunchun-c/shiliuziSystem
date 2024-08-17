package com.shiliuzi.personnel_management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.mapper.GroupMapper;
import com.shiliuzi.personnel_management.pojo.Group;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

    @Autowired
    GroupMapper groupMapper;

    @Override
    public Result getGroupIdByUserId(Integer UserId) {
        List<Integer> groupIdList = groupMapper.getGroupIdByUserId(UserId);
        if (!groupIdList.isEmpty()){
            return Result.success(groupIdList);
        }else {
            return Result.fail(AppExceptionCodeMsg.NO_FIT_DATA);
        }
    }
}
