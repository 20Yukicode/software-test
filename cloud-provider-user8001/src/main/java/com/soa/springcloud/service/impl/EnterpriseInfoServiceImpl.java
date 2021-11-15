package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entity.domain.EnterpriseInfo;
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
    public EnterpriseInfo getEnterpriseInfo(@Param("unified_id") int unified_id){
        return enterpriseInfoMapper.selectById(unified_id);
    }

    @Override
    public int updateEnterpriseInfo(EnterpriseInfo enterpriseInfo){
        QueryWrapper<EnterpriseInfo> wrapper=new QueryWrapper<>();
        wrapper.eq("unified_id",enterpriseInfo.getUnifiedId());
        return enterpriseInfoMapper.update(enterpriseInfo,wrapper);
    }
}
