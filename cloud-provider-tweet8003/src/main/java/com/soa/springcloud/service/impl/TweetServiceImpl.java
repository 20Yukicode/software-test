package com.soa.springcloud.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.Comment;
import com.soa.springcloud.entities.Tweet;
import com.soa.springcloud.mapper.CommentMapper;
import com.soa.springcloud.mapper.TweetMapper;
import com.soa.springcloud.service.TweetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TweetServiceImpl implements TweetService{

    @Resource
    private TweetMapper tweetMapper;

    @Resource
    private CommentMapper commentMapper;

    @Override
    public int addLikeNum(Integer tweetId){

        Tweet tweet = tweetMapper.selectById(tweetId);
        QueryWrapper<Tweet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tweet_id",tweetId);
        if(tweet == null)return -1;

        tweet.setPraiseNum(tweet.getPraiseNum()+1);
        tweetMapper.update(tweet,queryWrapper);
        return tweetMapper.selectById(tweetId).getPraiseNum();
    }
    @Override
    public int subtractLikeNum(Integer tweetId){
        Tweet tweet = tweetMapper.selectById(tweetId);
        QueryWrapper<Tweet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tweet_id",tweetId);
        if(tweet == null)return -1;

        tweet.setPraiseNum(tweet.getPraiseNum()-1);
        tweetMapper.update(tweet,queryWrapper);
        return tweetMapper.selectById(tweetId).getPraiseNum();
    }
    @Override
    public int deleteTweet(Integer tweetId){
        QueryWrapper<Tweet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tweet_id",tweetId);
        return tweetMapper.delete(queryWrapper);
    }

    public int getSelfTweetList(Integer intervieweeId, Integer momentId){
        QueryWrapper<Tweet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unified_id",intervieweeId).gt("moment_id",momentId);
        List tweetList = tweetMapper.selectList(queryWrapper);
        int size = tweetList.size();
        if(size>10) size = 9;
        JSONArray jsonArray = new JSONArray();

        for(int i=0;i<size; i++){
            JSONObject object = JSONUtil.parseObj(tweetList.get(i));

        }
    }



}
