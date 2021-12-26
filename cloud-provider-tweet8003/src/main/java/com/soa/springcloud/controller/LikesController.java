package com.soa.springcloud.controller;


import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.service.LikesService;
import com.soa.springcloud.service.TweetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
public class LikesController {

    @Resource
    private LikesService likesService;

    @Resource
    private  TweetService tweetService;

    /**
     * 点赞
     * @param unifiedId
     * @param tweetId
     * @return
     */
    @PutMapping("/tweet/likes")
    public CommonResult putLikes(@RequestParam Integer unifiedId,
                                @RequestParam Integer tweetId){

        if(unifiedId == null)
            return CommonResult.failure("点赞错误，缺少unifiedId");
        if(tweetId == null)
            return CommonResult.failure("点赞错误，缺少tweetId");
        if(likesService.putLikes(unifiedId,tweetId)>0){
            return CommonResult.success("点赞成功",tweetService.addLikesNum(tweetId));
        }
        return CommonResult.failure("点赞失败，未找到对应动态");

    }

    /**
     * 取赞
     * @param unifiedId
     * @param tweetId
     * @return
     */
    @DeleteMapping("/tweet/likes")
    public CommonResult deleteLikes(@RequestParam Integer unifiedId,
                                   @RequestParam Integer tweetId){

        if(unifiedId == null)
            return CommonResult.failure("取赞错误，缺少unifiedId");
        if(tweetId == null)
            return CommonResult.failure("取赞错误，缺少tweetId");


        if(likesService.deleteLikes(unifiedId,tweetId)>0){
            return CommonResult.success("取赞成功",tweetService.subtractLikesNum(tweetId));
        }
        return CommonResult.failure("取赞失败，未找到对应动态");

    }

    /**
     * 获取动态点赞数
     * @param unifiedId
     * @param tweetId
     * @return
     */
    @GetMapping("/tweet/likes")
    public CommonResult getLikes(@RequestParam Integer unifiedId,
                                @RequestParam Integer tweetId){

        if(unifiedId == null)
            return CommonResult.failure("错误，缺少unifiedId");
        if(tweetId == null)
            return CommonResult.failure("错误，缺少tweetId");

        int state = likesService.getLikes(unifiedId,tweetId);

        return CommonResult.success("查询成功",state);

    }

}
