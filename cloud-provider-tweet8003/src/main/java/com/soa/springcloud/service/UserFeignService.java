package com.soa.springcloud.service;

import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.Tweet;
import com.soa.springcloud.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "CLOUD-USER-SERVICE" /*,fallback = TweetFallbackService.class*/)
public interface UserFeignService {

    @RequestMapping(path = "/user/get/{unifiedId}", method= RequestMethod.GET)
    public CommonResult<User> UserById(@PathVariable("unifiedId") Integer unifiedId);
}
