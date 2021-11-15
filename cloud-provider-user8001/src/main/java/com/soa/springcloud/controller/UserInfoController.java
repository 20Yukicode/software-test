package com.soa.springcloud.controller;

import com.soa.springcloud.entity.domain.UserInfo;
import com.soa.springcloud.service.impl.UserInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class UserInfoController {
    @Resource
    private UserInfoServiceImpl userInfoService;

    @GetMapping("/userinfo/get/{unified_id}")
    public UserInfo getUserInfo(@PathVariable("unified_id") int unified_id) {
        UserInfo userInfo = userInfoService.getUserInfo(unified_id);
        log.info("***查询结果：" + userInfo);
        return userInfo;
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
