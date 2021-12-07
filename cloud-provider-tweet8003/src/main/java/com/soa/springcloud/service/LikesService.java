package com.soa.springcloud.service;

public interface LikesService {

    int getLikes(Integer unifiedId,Integer tweetId);
    int deleteLikes(Integer unifiedId,Integer tweetId);
    int putLikes(Integer unifiedId,Integer tweetId);
}
