package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.entities.UserInfo;
import com.soa.springcloud.mapper.UserInfoMapper;
import com.soa.springcloud.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;


    @Override
    public int create(Integer unifiedId){
        UserInfo userInfo = new UserInfo();
        userInfo.setUnifiedId(unifiedId);
        userInfo.setAge(0);
        userInfo.setGender("");
        userInfo.setIdCard("");
        userInfo.setLivePlace("");
        userInfo.setPhoneNum("");
        userInfo.setPrePosition("");
        int insert = userInfoMapper.insert(userInfo);

        return insert;
    }
    @Override
    public UserInfo getUserInfo(int unifiedId){
        return userInfoMapper.selectById(unifiedId);
    }
    @Override
    public int insertOrUpdateUserInfo(UserInfo userInfo) throws IllegalAccessException {
        if(userInfo==null)return 0;
        Integer unifiedId = userInfo.getUnifiedId();
        if(unifiedId==null)return 0;
        //若不存在该用户信息，则插入
        if(userInfoMapper.selectById(unifiedId)==null){
            return userInfoMapper.insert(userInfo);
        }
        UpdateWrapper<UserInfo> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("unified_id",userInfo.getUnifiedId());
        Class cls = userInfo.getClass();
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
            if(f.get(userInfo)!=null)num++;
            updateWrapper.set(f.get(userInfo)!=null,
                    name,f.get(userInfo));
        }
        if(num==0)updateWrapper.set("id_Card","");
        //updateWrapper.set(userInfo.get)
        return userInfoMapper.update(userInfo,updateWrapper);
        //return userInfoMapper.updateById(userInfo);
    }
}
