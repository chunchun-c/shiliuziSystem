package com.shiliuzi.personnel_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.personnel_management.pojo.RevokeApply;
import com.shiliuzi.personnel_management.result.Result;

public interface RevokeApplyService extends IService<RevokeApply> {

    Result getAllRevokeApply(Integer pn, Integer size);

    Result allow(Integer id);

    Result ignore(Integer id);
}
