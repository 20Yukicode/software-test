package com.soa.springcloud.service;


import com.soa.springcloud.entities.User;

public interface UserService {
    int create(User user);
    User getUserById( int unifiedId);
    User getUserByName(String userName);
}
