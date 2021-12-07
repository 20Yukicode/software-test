package com.soa.springcloud.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.soa.springcloud.entities.UserInfo;
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

    @GetMapping("/userinfo")
    public JSON getUserInfo(@RequestParam("uid") int unifiedId, @RequestParam("sid") int subscribeId) {
        int subscribed = subscriptionService.isSubscribed(unifiedId,subscribeId);
        if(unifiedId ==subscribeId)subscribed=2;
        UserInfo userInfo = userInfoService.getUserInfo(subscribeId);
        JSON json = JSONUtil.parse(userInfo);
        json.putByPath("isSubscribed",subscribed);
        //log.info("***查询结果：" + json);
        return json;
    }
    /**
     * 更改用户信息
     * @param userInfo
     * @return
     */
    @PutMapping(value = "/userinfo")
    public int updateUserInfo(@RequestBody UserInfo userInfo)
    {
        return userInfoService.updateUserInfo(userInfo);
    }
}
