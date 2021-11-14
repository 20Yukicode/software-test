package com.soa.springcloud.service.impl;


import com.soa.springcloud.dao.UserDao;
import com.soa.springcloud.entities.EnterpriseInfo;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.entities.UserInfo;
import com.soa.springcloud.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: PaymentServiceImpl
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Override
    public int create(User user) {
        return userDao.create(user);
    }
    @Override
    public User getUserById(int unified_id) {
        return userDao.getUserById(unified_id);
    }
    @Override
    public User getUserByName(String user_name) {
        return userDao.getUserByName(user_name);
    }
    @Override
    public UserInfo getUserInfo(@Param("unified_id") int unified_id){
        return userDao.getUserInfo(unified_id);
    }
    @Override
    public EnterpriseInfo getEnterpriseInfo(@Param("unified_id") int unified_id){
        return userDao.getEnterpriseInfo(unified_id);
    }
    @Override
    public int updateInfo(UserInfo userInfo){
        return 0;
    }

}
