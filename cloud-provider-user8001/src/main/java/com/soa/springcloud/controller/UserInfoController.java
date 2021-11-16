package com.soa.springcloud.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.soa.springcloud.entity.domain.UserInfo;
import com.soa.springcloud.service.impl.SubscriptionServiceImpl;
import com.soa.springcloud.service.impl.UserInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class UserInfoController {
    @Resource
    private UserInfoServiceImpl userInfoService;
    @Resource
    private SubscriptionServiceImpl subscriptionService;

    @GetMapping("/userinfo/get/{unified_id}/{subscribeId}")
    public JSON getUserInfo(@PathVariable("unified_id") int unified_id,@PathVariable("subscribeId") int subscribeId) throws JsonProcessingException {
        int subscribed = subscriptionService.isSubscribed(unified_id,subscribeId);
        if(unified_id==subscribeId)subscribed=2;
        UserInfo userInfo = userInfoService.getUserInfo(subscribeId);
        JSON json = JSONUtil.parse(userInfo);
        json.putByPath("is_subscribed",subscribed);
        log.info("***查询结果：" + json);
        return json;
    }
    /**
     * 更改用户信息
     * @param userInfo
     * @return
     */
    @PostMapping(value = "/userinfo/update")
    public int updateUserInfo(@RequestBody UserInfo userInfo)
    {
        return userInfoService.updateUserInfo(userInfo);
    }
}
