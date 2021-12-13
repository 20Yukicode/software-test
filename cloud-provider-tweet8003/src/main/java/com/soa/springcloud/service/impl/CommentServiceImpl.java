package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.Comment;
import com.soa.springcloud.entities.Tweet;
import com.soa.springcloud.mapper.CommentMapper;
import com.soa.springcloud.mapper.TweetMapper;
import com.soa.springcloud.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    @Resource
    private TweetMapper tweetMapper;

    @Override
    public List getComments(Integer tweetId){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tweet_id",tweetId);
        return commentMapper.selectList(queryWrapper);
    }

    @Override
    public int postComment(Comment comment){
        Integer maxFloor = commentMapper.maxFloor(comment.getTweetId());
        int floor = 1;
        if(maxFloor != null)
            floor = maxFloor +1;
        comment.setFloor(floor);

        QueryWrapper<Tweet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tweet_id",comment.getTweetId());
        Tweet tweet = tweetMapper.selectOne(queryWrapper);
        tweet.setCommentNum(tweet.getCommentNum()+1);
        tweetMapper.update(tweet,queryWrapper);

        return commentMapper.insert(comment);
    }

    @Override
    public int deleteComment(Integer unifiedId,Integer tweetId,Integer floor){

        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unified_id",unifiedId).eq("tweet_id",tweetId).eq("floor",floor);

        QueryWrapper<Tweet> tweetQueryWrapper = new QueryWrapper<>();
        tweetQueryWrapper.eq("tweet_id",tweetId);
        Tweet tweet = tweetMapper.selectOne(tweetQueryWrapper);
        tweet.setCommentNum(tweet.getCommentNum()-1);
        tweetMapper.update(tweet,tweetQueryWrapper);

        return commentMapper.delete(queryWrapper);
    }
}
