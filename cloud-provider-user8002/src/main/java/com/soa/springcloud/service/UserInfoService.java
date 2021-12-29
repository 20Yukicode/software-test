package com.soa.springcloud.service;

import com.soa.springcloud.entities.UserInfo;

public interface UserInfoService {
    int create(Integer unifiedId);
    UserInfo getUserInfo(int unifiedId);
    int insertOrUpdateUserInfo(UserInfo userInfo) throws IllegalAccessException;
}
