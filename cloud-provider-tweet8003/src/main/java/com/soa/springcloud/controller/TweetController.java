package com.soa.springcloud.controller;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.service.TweetService;
import com.soa.springcloud.service.UserTweetFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class TweetController {
    @Resource
    private TweetService tweetService;

    @Resource
    private UserTweetFeignService userService;



    @GetMapping("test")
    public CommonResult test(){
        return userService.UserById(new Integer(1));
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
                                         @RequestParam(required = false) Integer momentId){
        if(visitorId == null)
            return CommonResult.failure("错误，visitorId为空");
        if(intervieweeId == null)
            return CommonResult.failure("错误，intervieweeId为空");

        if(momentId ==null)
            momentId=tweetService.maxTweetId();
        return CommonResult.success("查询成功",tweetService.getSelfTweetList(visitorId,intervieweeId,momentId));
    }
    //fallback
    public CommonResult tweetList_TimeOutHandler(@RequestParam Integer unifiedId, @RequestParam(required = false) Integer momentId,
                                                 @RequestParam(required = false) String type)
    {
        log.info("TweetList_Fallback");
        return CommonResult.failure("用户:"+unifiedId+" 调用 /tweet/tweetList 接口超时，请重试");
    }

    @HystrixCommand(fallbackMethod = "tweetList_TimeOutHandler"/*指定善后方法名*/,commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="8000")
    })
    @GetMapping("/tweet/tweetList")
    public CommonResult getTweetList(@RequestParam Integer unifiedId, @RequestParam(required = false) Integer momentId,
                                     @RequestParam(required = false) String type){

        if(unifiedId == null)
            return CommonResult.failure("错误，unifiedId为空");

        JSONArray jsonArray = tweetService.getTweetList(unifiedId,momentId);
        JSONArray array = new JSONArray();
        if(jsonArray.size()==0){
            return CommonResult.success("查询成功",array);
        }
        //若查询id为空，不做分割，返回前9个动态/启事
        if(momentId==null){
            for(int i=jsonArray.size()-1;i>=jsonArray.size()-9&&i>=0;i--){
                array.add(jsonArray.get(i));
            }
            return CommonResult.success("查询成功",array);
        }

        //若查询id不为空
        //根据岗位id划分
        if(type.equals("position")){
            for(int i=jsonArray.size()-1;i>=0;i--){
                if(jsonArray.get(i,JSONObject.class).get("type").equals("position")&&jsonArray.get(i,JSONObject.class)
                        .getInt("jobId")<=momentId){
                    for(int j=i-1;j>=i-9&&j>=0;j--){
                        array.add(jsonArray.get(j));
                    }
                    return CommonResult.success("查询成功",array);
                }
            }
        }
        //根据动态id划分
        if(type.equals("tweet")){
            for(int i=jsonArray.size()-1;i>=0;i--){
                if(jsonArray.get(i,JSONObject.class).get("type").equals("tweet")&&jsonArray.get(i,JSONObject.class)
                        .getInt("tweetId")<=momentId){
                    for(int j=i-1;j>=i-9&&j>=0;j--){
                        array.add(jsonArray.get(j));
                        log.info(jsonArray.get(j).toString());
                    }
                    return CommonResult.success("查询成功",array);
                }
            }
        }
        return CommonResult.success("查询成功",array);
    }

    @PutMapping("/tweet")
    public CommonResult createTweet(@RequestParam Integer unifiedId,
                                    @RequestParam String content,
                                    @RequestPart(value = "files",required = false) List<MultipartFile> files)throws IOException {
        if(unifiedId == null)
            return CommonResult.failure("错误，unifiedId为空");
        Date recordTime = new Date();
        recordTime.setHours(recordTime.getHours()+8);
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
