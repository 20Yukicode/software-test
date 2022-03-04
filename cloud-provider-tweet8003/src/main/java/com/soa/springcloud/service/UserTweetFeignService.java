package com.soa.springcloud.service;

import com.soa.springcloud.api.CommonResult;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.service.impl.UserFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "CLOUD-USER-SERVICE" ,fallback = UserFallbackService.class)
public interface UserTweetFeignService {

    @RequestMapping(path = "/user/get/{unifiedId}", method= RequestMethod.GET)
    public CommonResult<User> UserById(@PathVariable("unifiedId") Integer unifiedId);
}
