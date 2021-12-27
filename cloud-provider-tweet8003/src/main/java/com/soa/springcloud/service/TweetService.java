package com.soa.springcloud.service;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.soa.springcloud.entities.Tweet;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface TweetService {

    int addLikesNum(Integer tweetId);
    int subtractLikesNum(Integer tweetId);
    int deleteTweet(Integer tweetId);
    JSONArray getSelfTweetList(Integer visitorId, Integer intervieweeId, Integer momentId);
    JSONArray getTweetList(Integer visitorId,Integer momentId);
    JSONObject getSimpleUserInfo(Integer unifiedId);
    List getTweetPictures(Integer tweetId);
    int createTweet(Integer unifiedId, String content, Date recordTime, List<MultipartFile> files)throws IOException;
    int uploadPic(Integer unifiedId,List<MultipartFile> files)throws IOException;
    int maxTweetId();
}
