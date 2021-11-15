package com.soa.springcloud.service;


import com.soa.springcloud.entity.domain.User;
import com.soa.springcloud.entity.domain.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface UserService {
    int create(User user);
    User getUserById(@Param("unified_id") int unified_id);
    User getUserByName(@Param("user_name") String user_name);
}
