package com.soa.springcloud.service.impl;

import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.service.UserFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserFallbackService implements UserFeignService {
    @Override
    public CommonResult<User> UserById(Integer unifiedId) {
        log.info("UserFeignService_FallBack");
        return CommonResult.failure("-----UserFallbackService fall back-userInfo_OK ,o(╥﹏╥)o");
    }
}
