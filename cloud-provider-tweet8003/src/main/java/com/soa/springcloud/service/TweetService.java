package com.soa.springcloud.service;
import java.util.List;

public interface TweetService {

    int addLikeNum(Integer tweetId);
    int subtractLikeNum(Integer tweetId);


}