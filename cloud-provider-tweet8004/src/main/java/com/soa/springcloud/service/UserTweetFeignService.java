package com.soa.springcloud.service;

import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.service.impl.UserFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "CLOUD-USER-SERVICE" ,fallback = UserFallbackService.class)
public interface UserTweetFeignService {

    @RequestMapping(path = "/user/get/{unifiedId}", method= RequestMethod.GET)
    public CommonResult<User> UserById(@PathVariable("unifiedId") Integer unifiedId);
}
