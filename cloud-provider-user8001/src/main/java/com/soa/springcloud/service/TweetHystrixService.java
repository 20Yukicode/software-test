package com.soa.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(value = "CLOUD-TWEET-SERVICE" /*,fallback = TweetFallbackService.class*/)
@Component
public interface TweetHystrixService {
    //直接写Tweet模块Controller层的方法
}
