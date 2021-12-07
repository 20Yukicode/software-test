package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.Like;
import com.soa.springcloud.entities.Subscription;
import com.soa.springcloud.entities.Tweet;
import com.soa.springcloud.mapper.LikeMapper;
import com.soa.springcloud.mapper.SubscriptionMapper;
import com.soa.springcloud.mapper.TweetMapper;
import com.soa.springcloud.service.LikeService;
import com.soa.springcloud.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class LikeServiceImpl implements LikeService{

    @Resource
    private  LikeMapper likeMapper;

    @Resource
    private TweetMapper tweetMapper;

    @Override
    public int getLike(Integer unifiedId,Integer tweetId){


        List list = likeMapper.selectByMap(new HashMap() {
            {
                put("unified_id", unifiedId);
                put("tweet_id", tweetId);
            }
        });
        return list.size();
    }

    @Override
    public int deleteLike(Integer unifiedId,Integer tweetId){
        QueryWrapper<Like> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unified_id",unifiedId).eq("tweet_id",tweetId);

        return likeMapper.delete(queryWrapper);
    }

    @Override
    public int putLike(Integer unifiedId,Integer tweetId){
        Like like = new Like();
        like.setTweetId(tweetId);
        like.setUnifiedId(unifiedId);


        return likeMapper.insert(like);
    }

}
