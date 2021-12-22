package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.EnterpriseInfo;
import com.soa.springcloud.mapper.EnterpriseInfoMapper;
import com.soa.springcloud.service.EnterpriseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class EnterpriseInfoServiceImpl implements EnterpriseInfoService {
    @Resource
    private EnterpriseInfoMapper enterpriseInfoMapper;
    @Override
    public int create(Integer unifiedId){
        EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
        enterpriseInfo.setUnifiedId(unifiedId);
        return enterpriseInfoMapper.insert(enterpriseInfo);
    }
    @Override
    public EnterpriseInfo getEnterpriseInfo(int unifiedId){
        return enterpriseInfoMapper.selectById(unifiedId);
    }

    @Override
    public int insertOrUpdateEnterpriseInfo(EnterpriseInfo enterpriseInfo){
        if(enterpriseInfo==null)return 0;
        Integer unifiedId = enterpriseInfo.getUnifiedId();
        if(unifiedId==null)return 0;
        //若不存在该用户信息，则插入
        if(enterpriseInfoMapper.selectById(unifiedId)==null){
            return enterpriseInfoMapper.insert(enterpriseInfo);
        }
        return enterpriseInfoMapper.updateById(enterpriseInfo);
    }
}
