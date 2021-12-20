package com.soa.springcloud.controller;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.service.SubscriptionService;
import com.soa.springcloud.service.TweetService;
import com.soa.springcloud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class TweetController {
    @Resource
    private TweetService tweetService;

    @Resource
    private UserService userService;

    @GetMapping("test")
    public CommonResult<User> test(){
        return userService.getUserById(1);
    }

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
        //jsonArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getInt("tweetId")));
        JSONArray array = new JSONArray();
//        return CommonResult.success("测试",jsonArray);
        int count = 0;
        for(int i=0;i<jsonArray.size();i++){
            JSONObject object = jsonArray.getJSONObject(i);
            int tweetId = object.getInt("tweetId");
            if(count<10 && tweetId>momentId ){
                object.put("pictureList",tweetService.getTweetPictures(tweetId));
                count++;
                array.add(object);
            }

        }
        return CommonResult.success("查询成功",array);
    }

    @PutMapping("/tweet")
    public CommonResult createTweet(@RequestParam Integer unifiedId,
                                    @RequestParam Date recordTime,
                                    @RequestParam String content,
                                    @RequestPart(value = "file",required = false) MultipartFile[] files)throws IOException {
        if(unifiedId == null)
            return CommonResult.failure("错误，unifiedId为空");
        if(recordTime == null)
            return CommonResult.failure("错误，recordTime为空");

        int tweetId = tweetService.createTweet(unifiedId, content, recordTime, files);
        return CommonResult.success("创建成功",tweetId);
    }

    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
