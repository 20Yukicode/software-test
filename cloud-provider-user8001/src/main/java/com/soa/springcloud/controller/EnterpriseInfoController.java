package com.soa.springcloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.soa.springcloud.api.CommonResult;
import com.soa.springcloud.entities.EnterpriseInfo;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.service.impl.EnterpriseInfoServiceImpl;
import com.soa.springcloud.service.impl.SubscriptionServiceImpl;
import com.soa.springcloud.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
//@CrossOrigin(origins = "*")
public class EnterpriseInfoController {
    @Resource
    private EnterpriseInfoServiceImpl enterpriseInfoService;
    @Resource
    private SubscriptionServiceImpl subscriptionService;
    @Resource
    private UserServiceImpl userService;
    /**
     * 获取企业信息
     * @param unifiedId
     * @return
     */
    @GetMapping("/user/enterpriseinfo")
    public CommonResult<JSONObject>  getEnterpriseInfo(@RequestParam("uid") int unifiedId, @RequestParam("sid") int subscribeId) {
        int subscribed = subscriptionService.isSubscribed(unifiedId,subscribeId);
        if(unifiedId ==subscribeId)subscribed=2;
        User sUser = userService.getUserById(subscribeId);
        if(!sUser.getUserType().equals("company")){
            return CommonResult.failure("查询类型错误");
        }
        EnterpriseInfo enterpriseInfo = enterpriseInfoService.getEnterpriseInfo(subscribeId);
        JSONObject json = (JSONObject) JSONObject.toJSON(enterpriseInfo);
        int fansNum = subscriptionService.fansNum(subscribeId);
        int followNum = subscriptionService.followNum(subscribeId);
        json.put("fansNum",fansNum);
        json.put("followNum",followNum);
        json.put("isSubscribed",subscribed);
        User user = userService.getUserById(subscribeId);
        json.put("trueName",user.getTrueName());
        json.put("pictureUrl",user.getPictureUrl());
        json.put("briefInfo",user.getBriefInfo());
        json.put("email",user.getEmail());
        json.put("background",user.getBackground());
        //log.info("***查询结果：" + json);
        if(json==null)return CommonResult.failure();
        return CommonResult.success(json);
    }
    /**
     * 更改企业信息
     * @param
     * @return
     */
    @PostMapping(value = "/user/enterpriseinfo")
    public CommonResult updateEnterpriseInfo(@RequestBody JSONObject jsonObject) throws IllegalAccessException {
        Integer unifiedId = jsonObject.getInteger("unifiedId");
        if(unifiedId==null){
            return CommonResult.failure("企业用户id不能为空");
        }
        String briefInfo = jsonObject.getString("briefInfo");
        String trueName = jsonObject.getString("trueName");
        User user = new User();
        user.setUnifiedId(unifiedId);
        user.setBriefInfo(briefInfo);
        user.setTrueName(trueName);
        EnterpriseInfo enterpriseInfo = JSONObject.toJavaObject(jsonObject,EnterpriseInfo.class);
        //该用户不存在
        if(userService.getUserById(unifiedId)==null){
            return CommonResult.failure("用户不存在");
        }
        int result = 1;
        if(briefInfo!=null||trueName!=null){
            result=userService.update(user);
        }
        result =  result & enterpriseInfoService.insertOrUpdateEnterpriseInfo(enterpriseInfo);

        if(result>=1){
            return CommonResult.success();
        }
        return CommonResult.failure();

    }
}
