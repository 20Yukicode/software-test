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
    public int updateUserInfo(UserInfo userInfo){
        QueryWrapper<UserInfo> wrapper=new QueryWrapper<>();
        wrapper.eq("unified_id",userInfo.getUnifiedId());
        return userInfoMapper.update(userInfo,wrapper);
    }
}
