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

    @PutMapping("/tweet/subscription")
    public CommonResult putSubscription(@RequestParam Integer unifiedId,
                                        @RequestParam Integer subscribeId){

        if(unifiedId == null)
            return CommonResult.failure("失败，unified_id为空");
        if(subscribeId == null)
            return  CommonResult.failure("失败，subscribe_id为空");

        if(subscriptionService.putSubscription(unifiedId,subscribeId)>0)
            return CommonResult.success("关注成功",null);
        if(subscriptionService.putSubscription(unifiedId,subscribeId)==-1)
            return CommonResult.success("已经关注",null);
        return CommonResult.failure("关注失败");
    }

    @DeleteMapping("/tweet/subscription")
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

    @GetMapping("/tweet/subscription")
    public CommonResult getSubscription(@RequestParam Integer unifiedId,
                                        @RequestParam Integer subscribeId){
        if(unifiedId == null)
            return CommonResult.failure("失败，unifiedId为空");

        if(subscribeId == null)
            return CommonResult.failure("失败，subscribeId为空");

        if(subscriptionService.getSubscription(unifiedId,subscribeId)>0)
            return CommonResult.success("已关注该用户",null);

        return CommonResult.failure("未关注该用户");

    }

    @GetMapping("/tweet/subscriptionList/{unifiedId}")
    public CommonResult getSubscriptionList(@PathVariable Integer unifiedId){

        if(unifiedId == null)
            return CommonResult.failure("错误，unifiedId为空");

        List subscriptionList = subscriptionService.getSubscriptionList(unifiedId);

        return CommonResult.success(subscriptionList);
    }

    @GetMapping("/tweet/recommend/{unifiedId}")
    public CommonResult getRecommendList(@PathVariable Integer unifiedId){
        if(unifiedId == null)
            return CommonResult.failure("错误，unifiedId为空");

        return CommonResult.success("请求成功",subscriptionService.getRecommendList(unifiedId));
    }
}
