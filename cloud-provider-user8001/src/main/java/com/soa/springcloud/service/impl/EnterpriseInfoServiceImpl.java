package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.soa.springcloud.entities.EnterpriseInfo;
import com.soa.springcloud.entities.UserInfo;
import com.soa.springcloud.mapper.EnterpriseInfoMapper;
import com.soa.springcloud.service.EnterpriseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
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
        if(unifiedId==null)return 0;
        //若不存在该用户信息，则插入
        if(enterpriseInfoMapper.selectById(unifiedId)==null){
            return enterpriseInfoMapper.insert(enterpriseInfo);
        }
        UpdateWrapper<EnterpriseInfo> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("unified_id",enterpriseInfo.getUnifiedId());
        Class cls = enterpriseInfo.getClass();
        Field[] fields = cls.getDeclaredFields();
        int num = 0;
        for(int i=0; i<fields.length; i++){
            Field f = fields[i];
            if(f.getName()=="unifiedId"||f.getName()=="serialVersionUID"){
                continue;
            }
            f.setAccessible(true);
            String name = f.getName();
            for(int j=0;j<f.getName().length();j++){
                if(name.charAt(j)>='A'&&name.charAt(j)>='Z'){
                    name.replaceFirst(String.valueOf(name.charAt(j)),"_"+String.valueOf(name.charAt(j)).toLowerCase());
                    break;
                }
            }
            if(f.get(enterpriseInfo)!=null)num++;
            updateWrapper.set(f.get(enterpriseInfo)!=null,
                    name,f.get(enterpriseInfo));
        }
        if(num==0)updateWrapper.set("description","");
        return enterpriseInfoMapper.update(enterpriseInfo,updateWrapper);
    }
}
