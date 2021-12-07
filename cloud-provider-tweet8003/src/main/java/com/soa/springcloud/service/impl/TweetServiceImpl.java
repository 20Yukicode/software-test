package com.soa.springcloud.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.Comment;
import com.soa.springcloud.entities.Picture;
import com.soa.springcloud.entities.Tweet;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.mapper.*;
import com.soa.springcloud.service.LikeService;
import com.soa.springcloud.service.SubscriptionService;
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

    @Resource
    private LikeService likeService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private PictureMapper pictureMapper;

    private SubscriptionService subscriptionService;
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

    @Override
    public JSONArray getSelfTweetList(Integer visitorId,Integer intervieweeId, Integer momentId){
        QueryWrapper<Tweet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unified_id",intervieweeId).gt("moment_id",momentId);
        List tweetList = tweetMapper.selectList(queryWrapper);
        int size = tweetList.size();
        if(size>10) size = 9;
        JSONArray jsonArray = new JSONArray();

        for(int i=0;i<size; i++){
            JSONObject object = JSONUtil.parseObj(tweetList.get(i));
            object.put("like_state",likeService.getLike(visitorId,object.getInt("tweet_id")));
            jsonArray.add(object);
        }
        return jsonArray;
    }

    @Override
    public JSONArray getTweetList(Integer visitorId, Integer momentId){

        List subscriptionList = subscriptionService.getSubscriptionList(visitorId);
        JSONArray tweetArray = new JSONArray();
        QueryWrapper<Tweet> queryWrapper = new QueryWrapper<>();
        for(int i=0;i<subscriptionList.size();i++){
            JSONObject object = JSONUtil.parseObj(subscriptionList.get(i));
            queryWrapper.eq("unified_id",object.getInt("subscribe_id"));
            List list = tweetMapper.selectList(queryWrapper);
            for(int j=0;j<list.size();j++){
                JSONObject tweetObject = JSONUtil.parseObj(list.get(j));
                tweetObject.put("like_state",likeService.getLike(visitorId,object.getInt("tweet_id")));
                tweetObject.put("simpleUserInfo",getSimpleUserInfo(object.getInt("unified_id")));
                tweetArray.add(tweetObject);
            }
        }
        return tweetArray;

    }
    @Override
    public JSONObject getSimpleUserInfo(Integer unifiedId){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unified_id",unifiedId);
        User user = userMapper.selectOne(queryWrapper);
        if(user != null) {
            JSONObject object = new JSONObject();
            object.put("picture_url", user.getPictureUrl());
            object.put("true_name", user.getTrueName());
            object.put("brief_info", user.getBriefInfo());
            object.put("user_type", user.getUserType());
            return object;
        }
        return null;

    }

    @Override
    public List getTweetPictures(Integer tweetId){
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tweet_id",tweetId);
        return pictureMapper.selectList(queryWrapper);

    }


}
