package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.UserInfo;
import com.soa.springcloud.mapper.UserInfoMapper;
import com.soa.springcloud.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public int create(Integer unifiedId){
        UserInfo userInfo = new UserInfo();
        userInfo.setUnifiedId(unifiedId);
        //log.info("userinfo:"+userInfo);
        int insert = userInfoMapper.insert(userInfo);
        return insert;
    }
    @Override
    public UserInfo getUserInfo(int unifiedId){
        return userInfoMapper.selectById(unifiedId);
    }
    @Override
    public int insertOrUpdateUserInfo(UserInfo userInfo){
        if(userInfo==null)return 0;
        Integer unifiedId = userInfo.getUnifiedId();
        if(unifiedId==null)return 0;
        //若不存在该用户信息，则插入
        if(userInfoMapper.selectById(unifiedId)==null){
            return userInfoMapper.insert(userInfo);
        }
        return userInfoMapper.updateById(userInfo);
    }
}
