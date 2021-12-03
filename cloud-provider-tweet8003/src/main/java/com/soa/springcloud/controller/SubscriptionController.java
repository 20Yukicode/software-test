package com.soa.springcloud.controller;


import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService){
        this.subscriptionService = subscriptionService;
    }

    @PutMapping("/user/subscription")
    public CommonResult putSubscription(@RequestParam("unified_id") Integer unifiedId,
                                        @RequestParam("subscribe_id") Integer subscribeId){

        if(unifiedId == null)
            return CommonResult.failure("失败，unified_id为空");
        if(subscribeId == null)
            return  CommonResult.failure("失败，subscribe_id为空");

        if(subscriptionService.putSubscription(unifiedId,subscribeId)>0)
            return CommonResult.success("关注成功");

        return CommonResult.failure("关注失败");
    }

    @DeleteMapping("/user/subscription")
    public CommonResult deleteSubscription(@RequestParam Integer unifiedId,
                                           @RequestParam Integer subscribeId){

        if(unifiedId == null)
            return CommonResult.failure("失败，unifiedId为空");

        if(subscribeId == null)
            return CommonResult.failure("失败，subscribeId为空");

        if(subscriptionService.deleteSubscription(unifiedId,subscribeId)>0)
            return CommonResult.success("取消关注成功");

        return CommonResult.failure("取消关注失败");
    }

    @GetMapping("/user/subscription")
    public CommonResult getSubscription(@RequestParam Integer unifiedId,
                                        @RequestParam Integer subscribeId){
        if(unifiedId == null)
            return CommonResult.failure("失败，unifiedId为空");

        if(subscribeId == null)
            return CommonResult.failure("失败，subscribeId为空");

        if(subscriptionService.getSubscription(unifiedId,subscribeId)>0)
            return CommonResult.success("已关注该用户");

        return CommonResult.failure("未关注该用户");

    }

    @GetMapping("/user/subscriptionList/{unifiedId}")
    public CommonResult getSubscriptionList(@PathVariable Integer unifiedId){

        if(unifiedId == null)
            return CommonResult.failure("错误，unifiedId为空");

        List subscriptionList = subscriptionService.getSubscriptionList(unifiedId);

        return CommonResult.success(subscriptionList);
    }
}