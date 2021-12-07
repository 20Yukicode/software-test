package com.soa.springcloud.controller;


import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.service.SubscriptionService;
import com.soa.springcloud.service.TweetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class TweetController {
    @Resource
    private TweetService tweetService;

    @DeleteMapping("/tweet")
    public CommonResult deleteTweet(Integer tweetId){
        if(tweetId == null)
            return CommonResult.failure("错误，tweetId为空");

        if(tweetService.deleteTweet(tweetId)>0){
            return CommonResult.success("删除成功",null);
        }
        return CommonResult.failure("删除失败");
    }


}
