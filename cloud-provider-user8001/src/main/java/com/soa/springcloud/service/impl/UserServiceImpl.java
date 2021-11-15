package com.soa.springcloud.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.EnterpriseInfo;
import com.soa.springcloud.entity.domain.User;
import com.soa.springcloud.entity.domain.UserInfo;
import com.soa.springcloud.mapper.UserMapper;
import com.soa.springcloud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: PaymentServiceImpl
 * @description:
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public int create(User user) {

        return userMapper.insert(user);
    }
    @Override
    public User getUserById(int unified_id) {
        return userMapper.selectById(unified_id);
    }
    @Override
    public User getUserByName(String user_name) {
        QueryWrapper<User>wrapper=new QueryWrapper<>();
        wrapper.eq("user_name",user_name);
        return userMapper.selectOne(wrapper);
    }
//    @Override
//    public UserInfo getUserInfo(@Param("unified_id") int unified_id){
//        return userMapper.getUserInfo(unified_id);
//    }
//    @Override
//    public EnterpriseInfo getEnterpriseInfo(@Param("unified_id") int unified_id){
//        return userMapper.getEnterpriseInfo(unified_id);
//    }
//    @Override
//    public int updateUserInfo(UserInfo userInfo){
//        return userMapper.updateUserInfo(userInfo);
//    }

}
