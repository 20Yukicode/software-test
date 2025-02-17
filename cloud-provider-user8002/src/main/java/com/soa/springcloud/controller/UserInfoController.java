package com.soa.springcloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.soa.springcloud.api.CommonResult;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.entities.UserInfo;
import com.soa.springcloud.service.impl.SubscriptionServiceImpl;
import com.soa.springcloud.service.impl.UserInfoServiceImpl;
import com.soa.springcloud.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
//@CrossOrigin(origins = "*")
public class UserInfoController {
    @Resource
    private UserInfoServiceImpl userInfoService;
    @Resource
    private SubscriptionServiceImpl subscriptionService;
    @Resource
    private UserServiceImpl userService;

    @GetMapping("/user/userinfo")
    public CommonResult<JSONObject> getUserInfo(@RequestParam("uid") int unifiedId, @RequestParam("sid") int subscribeId) {
        int subscribed = subscriptionService.isSubscribed(unifiedId,subscribeId);

        if(unifiedId ==subscribeId)subscribed=2;
        User sUser = userService.getUserById(subscribeId);
        if(!sUser.getUserType().equals("user")){
            return CommonResult.failure("查询类型错误");
        }
        UserInfo userInfo = userInfoService.getUserInfo(subscribeId);
        JSONObject json = (JSONObject) JSONObject.toJSON(userInfo);
        json.put("isSubscribed",subscribed);
        User user =userService.getUserById(subscribeId);
        json.put("trueName",user.getTrueName());
        json.put("pictureUrl",user.getPictureUrl());
        json.put("briefInfo",user.getBriefInfo());
        json.put("email",user.getEmail());
        json.put("background",user.getBackground());
        int fansNum = subscriptionService.fansNum(subscribeId);
        int followNum = subscriptionService.followNum(subscribeId);
        json.put("fansNum",fansNum);
        json.put("followNum",followNum);
        if(json==null)return CommonResult.failure();
        return CommonResult.success(json);
    }
    /**
     * 插入或更改用户信息
     * @param
     * @return
     */
    @PostMapping(value = "/user/userinfo")
    public CommonResult updateUserInfo(@RequestBody JSONObject jsonObject) throws IllegalAccessException {
        Integer unifiedId = jsonObject.getInteger("unifiedId");
        String briefInfo =jsonObject.getString("briefInfo");
        String trueName =jsonObject.getString("trueName");
        if(unifiedId==null)
            return CommonResult.failure("unifiedId空");
        User user = JSONObject.toJavaObject(jsonObject,User.class);
        UserInfo userInfo = JSONObject.toJavaObject(jsonObject,UserInfo.class);
        //该用户不存在
        if(userService.getUserById(unifiedId)==null){
            return CommonResult.failure("用户不存在");
        }
        int userUpdate=1;
        if(briefInfo!=null||trueName!=null) {
            userUpdate = userService.update(user);
        }
        int result =  userUpdate & userInfoService.insertOrUpdateUserInfo(userInfo);

        if(result>=1){
            return CommonResult.success("更新成功");
        }
        return CommonResult.failure("更新失败");
    }
}
