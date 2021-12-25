package com.soa.springcloud.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.EnterpriseInfo;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.entities.UserInfo;
import com.soa.springcloud.service.impl.EnterpriseInfoServiceImpl;
import com.soa.springcloud.service.impl.SubscriptionServiceImpl;
import com.soa.springcloud.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
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
    public CommonResult<JSON>  getEnterpriseInfo(@RequestParam("uid") int unifiedId, @RequestParam("sid") int subscribeId) {
        int subscribed = subscriptionService.isSubscribed(unifiedId,subscribeId);
        if(unifiedId ==subscribeId)subscribed=2;
        EnterpriseInfo enterpriseInfo = enterpriseInfoService.getEnterpriseInfo(subscribeId);
        JSON json = JSONUtil.parse(enterpriseInfo);
        json.putByPath("is_subscribed",subscribed);
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
        String briefInfo = jsonObject.getString("briefInfo");
        User user = new User();
        user.setUnifiedId(unifiedId);
        user.setBriefInfo(briefInfo);
        EnterpriseInfo enterpriseInfo = JSONObject.toJavaObject(jsonObject,EnterpriseInfo.class);
        //该用户不存在
        if(userService.getUserById(unifiedId)==null){
            return CommonResult.failure("用户不存在");
        }
        int result = userService.update(user) & enterpriseInfoService.insertOrUpdateEnterpriseInfo(enterpriseInfo);

        if(result>=1){
            return CommonResult.success();
        }
        return CommonResult.failure();

    }
}
