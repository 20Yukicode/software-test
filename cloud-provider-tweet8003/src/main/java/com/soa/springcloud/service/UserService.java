package com.soa.springcloud.service;

import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.Tweet;
import com.soa.springcloud.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-USER-SERVICE" /*,fallback = TweetFallbackService.class*/)
public interface UserService {

    @GetMapping(value = "/user/get/{unifiedId}")
    public CommonResult<User> getUserById(@PathVariable("unifiedId") int unifiedId);
}
