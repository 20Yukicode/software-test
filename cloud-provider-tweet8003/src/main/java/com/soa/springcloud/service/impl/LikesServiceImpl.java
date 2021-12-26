package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.Likes;
import com.soa.springcloud.mapper.LikesMapper;
import com.soa.springcloud.mapper.TweetMapper;
import com.soa.springcloud.service.LikesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class LikesServiceImpl implements LikesService {

    @Resource
    private LikesMapper likesMapper;

    @Resource
    private TweetMapper tweetMapper;

    @Override
    public int getLikes(Integer unifiedId,Integer tweetId){


        List list = likesMapper.selectByMap(new HashMap() {
            {
                put("unified_id", unifiedId);
                put("tweet_id", tweetId);
            }
        });
        return list.size();
    }


    public int deleteLikes(Integer unifiedId,Integer tweetId){
        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unified_id",unifiedId).eq("tweet_id",tweetId);

        return likesMapper.delete(queryWrapper);
    }

    public int putLikes(Integer unifiedId,Integer tweetId){
        Likes likes = new Likes();
        likes.setTweetId(tweetId);
        likes.setUnifiedId(unifiedId);
        return likesMapper.insert(likes);
    }

}
