package com.soa.springcloud.service;

import com.soa.springcloud.entities.Tweet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "CLOUD-TWEET-SERVICE" /*,fallback = TweetFallbackService.class*/)
@Component
public interface TweetHystrixService {
    //直接写Tweet模块Controller层的方法
    @GetMapping(value = "/tweet/{unified_id}")
    public Tweet getTweetById(@PathVariable("id") int unified_id);
}
