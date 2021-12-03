package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.Comment;
import com.soa.springcloud.entities.Like;
import com.soa.springcloud.entities.Subscription;
import com.soa.springcloud.entities.Tweet;
import com.soa.springcloud.mapper.CommentMapper;
import com.soa.springcloud.mapper.LikeMapper;
import com.soa.springcloud.mapper.SubscriptionMapper;
import com.soa.springcloud.mapper.TweetMapper;
import com.soa.springcloud.service.CommentService;
import com.soa.springcloud.service.LikeService;
import com.soa.springcloud.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    @Override
    public List getComments(Integer tweetId){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tweet_id",tweetId);
        return commentMapper.selectList(queryWrapper);
    }

    @Override
    public int postComment(Comment comment){
        return 0;
    }

    @Override
    public int deleteComment(Integer unifiedId,Integer tweetId,Integer floor){
        return 0;
    }
}
