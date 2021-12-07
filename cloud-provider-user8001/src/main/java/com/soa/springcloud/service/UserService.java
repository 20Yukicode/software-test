package com.soa.springcloud.service;


import com.soa.springcloud.entities.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {
    int create(User user);
    User getUserById( int unifiedId);
    User getUserByName(String userName);
}
