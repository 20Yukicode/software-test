package com.soa.springcloud.service.impl;

import com.soa.springcloud.entities.EnterpriseInfo;
import com.soa.springcloud.mapper.EnterpriseInfoMapper;
import com.soa.springcloud.service.EnterpriseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;

@Service
@Slf4j
public class EnterpriseInfoServiceImpl implements EnterpriseInfoService {
    @Resource
    private EnterpriseInfoMapper enterpriseInfoMapper;
    @Override
    public int create(Integer unifiedId){
        EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
        enterpriseInfo.setUnifiedId(unifiedId);
        enterpriseInfo.setContactWay("");
        enterpriseInfo.setDescription("");
        return enterpriseInfoMapper.insert(enterpriseInfo);
    }
    @Override
    public EnterpriseInfo getEnterpriseInfo(int unifiedId){
        return enterpriseInfoMapper.selectById(unifiedId);
    }

    @Override
    public int insertOrUpdateEnterpriseInfo(EnterpriseInfo enterpriseInfo) throws IllegalAccessException {
        if(enterpriseInfo==null)return 0;
        Integer unifiedId = enterpriseInfo.getUnifiedId();
        //若不存在该用户信息，则插入
        if(enterpriseInfoMapper.selectById(unifiedId)==null){
            return enterpriseInfoMapper.insert(enterpriseInfo);
        }
        Class cls = enterpriseInfo.getClass();
        Field[] fields = cls.getDeclaredFields();
        int num = 0;
        for(int i=0; i<fields.length; i++){
            Field f = fields[i];
            if(f.getName()=="unifiedId"||f.getName()=="serialVersionUID"){
                continue;
            }
            f.setAccessible(true);
            if(f.get(enterpriseInfo)!=null)num++;

        }
        if(num==0)enterpriseInfo.setContactWay("");
        return enterpriseInfoMapper.updateById(enterpriseInfo);
    }
}
