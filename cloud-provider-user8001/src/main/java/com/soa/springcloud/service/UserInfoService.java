package com.soa.springcloud.service;

import com.soa.springcloud.entity.domain.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface UserInfoService {
    int create(Integer unifiedId);
    UserInfo getUserInfo(@Param("unified_id") int unified_id);
    int updateUserInfo(UserInfo userInfo);
}
