package com.shiliuzi.personnel_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.personnel_management.exception.AppException;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.mapper.RPRecordMapper;
import com.shiliuzi.personnel_management.mapper.RevokeApplyMapper;
import com.shiliuzi.personnel_management.pojo.RPRecords;
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

    @Override
    public Result allow(Integer id) {
        //先更改申请表的状态
        UpdateWrapper<RevokeApply> revokeApplyUpdateWrapper = new UpdateWrapper<>();
        revokeApplyUpdateWrapper.eq("id",id).set("state",1);

        //判断申请表是否更改成功
        if(revokeApplyMapper.update(null,revokeApplyUpdateWrapper) > 0){

            //再更改奖惩记录里的是否撤销字段
            UpdateWrapper<RPRecords> rpRecordsUpdateWrapper = new UpdateWrapper<>();
            rpRecordsUpdateWrapper.eq("id",id).set("is_revoke",1);

            //判断是否成功更改奖惩记录表
            if(rpRecordMapper.update(null,rpRecordsUpdateWrapper)>0){
                return Result.success("同意撤销");
            }else {
                //更改奖惩记录is_revoke字段失败
                throw new AppException(AppExceptionCodeMsg.APPLY_REVOKE_ERROR);
            }
        }else {
            return Result.fail("同意撤销失败");
        }

    }

    @Override
    public Result ignore(Integer id) {
        //先更改申请表的状态
        UpdateWrapper<RevokeApply> revokeApplyUpdateWrapper = new UpdateWrapper<>();
        revokeApplyUpdateWrapper.eq("id",id).set("state",2);

        //判断申请表是否更改成功
        if(revokeApplyMapper.update(null,revokeApplyUpdateWrapper) > 0){

           return Result.success("忽略申请成功");
        }else {
            return Result.fail("忽略申请失败");
        }
    }
}
