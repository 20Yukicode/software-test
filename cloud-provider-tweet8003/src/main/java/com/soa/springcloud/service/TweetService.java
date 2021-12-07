package com.soa.springcloud.service;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import java.util.List;

public interface TweetService {

    int addLikeNum(Integer tweetId);
    int subtractLikeNum(Integer tweetId);
    int deleteTweet(Integer tweetId);
    JSONArray getSelfTweetList(Integer visitorId, Integer intervieweeId, Integer momentId);
    JSONArray getTweetList(Integer visitorId,Integer momentId);
    JSONObject getSimpleUserInfo(Integer unifiedId);
    List getTweetPictures(Integer tweetId);
}
