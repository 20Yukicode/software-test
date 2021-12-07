package com.soa.springcloud.controller;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.service.SubscriptionService;
import com.soa.springcloud.service.TweetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class TweetController {
    @Resource
    private TweetService tweetService;

    @DeleteMapping("/tweet/{tweetId}")
    public CommonResult deleteTweet(@PathVariable Integer tweetId){
        if(tweetId == null)
            return CommonResult.failure("错误，tweetId为空");

        if(tweetService.deleteTweet(tweetId)>0){
            return CommonResult.success("删除成功",null);
        }
        return CommonResult.failure("删除失败");
    }

    @GetMapping("/tweet/self")
    public CommonResult getSelfTweetList(@RequestParam Integer visitorId,
                                         @RequestParam Integer intervieweeId,
                                         @RequestParam Integer momentId){
        if(visitorId == null)
            return CommonResult.failure("错误，visitorId为空");
        if(intervieweeId == null)
            return CommonResult.failure("错误，intervieweeId为空");
        if(momentId == null)
            return CommonResult.failure("错误，momentId为空");

        return CommonResult.success("查询成功",tweetService.getSelfTweetList(visitorId,intervieweeId,momentId));
    }

    @GetMapping("/tweet/tweetList")
    public CommonResult getTweetList(@RequestParam Integer unifiedId, @RequestParam Integer momentId){
        if(momentId == null)
            return CommonResult.failure("错误，momentId为空");
        if(unifiedId == null)
            return CommonResult.failure("错误，unifiedId为空");

        JSONArray jsonArray = tweetService.getTweetList(unifiedId,momentId);
        JSONArray array = new JSONArray();
        int count = 0;
        for(int i=0;i<jsonArray.size();i++){
            JSONObject object = (JSONObject) jsonArray.get(i);
            int tweetId = (Integer)object.get("tweet_id");
            if(count<10 && tweetId>momentId ){
                object.put("pictureList",tweetService.getTweetPictures(tweetId));
                count++;
                array.add(object);
            }

        }
        return CommonResult.success("查询成功",array);
    }
}
