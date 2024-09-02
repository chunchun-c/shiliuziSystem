package com.shiliuzi.personnel_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.personnel_management.mapper.RPRecordMapper;
import com.shiliuzi.personnel_management.mapper.RevokeApplyMapper;
import com.shiliuzi.personnel_management.pojo.RevokeApply;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.RevokeApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @description:
 * @author: chun
 **/

@Service
public class RevokeApplyServiceImpl extends ServiceImpl<RevokeApplyMapper, RevokeApply> implements RevokeApplyService {

    @Autowired
    RevokeApplyMapper revokeApplyMapper;

    @Autowired
    RPRecordMapper rpRecordMapper;
    //获取所有信息
    @Override
    public Result getAllRevokeApply(Integer pn, Integer size) {
        Page<RevokeApply> page = new Page<>(pn, size);

        // 创建查询条件
        QueryWrapper<RevokeApply> queryWrapper = new QueryWrapper<>();

        // 执行分页查询
        Page<RevokeApply> resultPage = revokeApplyMapper.selectPage(page, queryWrapper);

        // 返回分页结果
        return Result.success(resultPage);



     /*   //获取所有的rpId
        List<Integer> rpIdList = revokeApplyList.stream()//转成流去操作
                .map(RevokeApply::getRpId)
                .distinct()  //去重
                .collect(Collectors.toList());

        //反转list
        Collections.reverse(rpIdList);
        */
    }
}
