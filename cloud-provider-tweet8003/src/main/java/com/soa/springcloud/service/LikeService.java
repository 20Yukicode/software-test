package com.soa.springcloud.service;

public interface LikeService {

    int getLike(Integer unifiedId,Integer tweetId);
    int deleteLike(Integer unifiedId,Integer tweetId);
    int putLike(Integer unifiedId,Integer tweetId);
}
