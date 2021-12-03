package com.soa.springcloud.controller;


import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.service.LikeService;
import com.soa.springcloud.service.TweetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class LikeController {

    @Resource
    private  LikeService likeService;

    @Resource
    private  TweetService tweetService;




    @PutMapping("/user/like")
    public CommonResult putLike(@RequestParam Integer unifiedId,
                                @RequestParam Integer tweetId){

        if(unifiedId == null)
            return CommonResult.failure("点赞错误，缺少unifiedId");
        if(tweetId == null)
            return CommonResult.failure("点赞错误，缺少tweetId");


        if(likeService.putLike(unifiedId,tweetId)>0){

            return CommonResult.success("点赞成功",tweetService.addLikeNum(tweetId));
        }
        return CommonResult.failure("点赞失败，未找到对应动态");

    }

    @DeleteMapping("/user/like")
    public CommonResult deleteLike(@RequestParam Integer unifiedId,
                                   @RequestParam Integer tweetId){

        if(unifiedId == null)
            return CommonResult.failure("取赞错误，缺少unifiedId");
        if(tweetId == null)
            return CommonResult.failure("取赞错误，缺少tweetId");


        if(likeService.deleteLike(unifiedId,tweetId)>0){
            return CommonResult.success("取赞成功",tweetService.subtractLikeNum(tweetId));
        }
        return CommonResult.failure("取赞失败，未找到对应动态");

    }

    @GetMapping("/user/like")
    public CommonResult getLike(@RequestParam Integer unifiedId,
                                @RequestParam Integer tweetId){

        if(unifiedId == null)
            return CommonResult.failure("错误，缺少unifiedId");
        if(tweetId == null)
            return CommonResult.failure("错误，缺少tweetId");

        int state = likeService.getLike(unifiedId,tweetId);

        return CommonResult.success("查询成功",state);

    }

}
