package com.shiliuzi.personnel_management;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiliuzi.personnel_management.mapper.GroupMapper;
import com.shiliuzi.personnel_management.mapper.UserGroupMapper;
import com.shiliuzi.personnel_management.pojo.Group;
import com.shiliuzi.personnel_management.pojo.RPRecords;
import com.shiliuzi.personnel_management.pojo.UserGroup;
import com.shiliuzi.personnel_management.service.RPRecordService;
import com.shiliuzi.personnel_management.utils.JwtUtil;
import com.shiliuzi.personnel_management.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test {

    @org.junit.jupiter.api.Test
    public void md5test() {
        String password = "123456";
        String name="张三";
        System.out.println(new MD5Util().EncodeByMd5(name,password));
    }

    @Autowired
    UserGroupMapper userGroupMapper;

    @Autowired
    GroupMapper groupMapper;

    @org.junit.jupiter.api.Test
    public void test2() {
//        QueryWrapper<UserGroup> userGroupWrapper = new QueryWrapper<>();
//        userGroupWrapper.select("group_id").eq("user_id", 1);
//        UserGroup userGroup = userGroupMapper.selectOne(userGroupWrapper);
//        System.out.println(userGroup);
        QueryWrapper<Group> groupWrapper = new QueryWrapper<>();
        groupWrapper.select("name").eq("id", 2);
        Group group = groupMapper.selectOne(groupWrapper);
        System.out.println(group);
    }

    @org.junit.jupiter.api.Test
    public void test3(){
        System.out.println(JwtUtil.makeToken("1", "7115beccdfa9d22152c0482501fee578"));
    }


    @Autowired
    RPRecordService rpRecordService;
    @org.junit.jupiter.api.Test
    public void test4(){
        Page<RPRecords> rpRecordsPage=new Page<>(1,2);
        Page<RPRecords> page = rpRecordService.page(rpRecordsPage);
        System.out.println(page.getRecords());
        System.out.println(page.getTotal());

    }
}
