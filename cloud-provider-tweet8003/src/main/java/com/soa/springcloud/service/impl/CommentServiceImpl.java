package com.soa.springcloud.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.Comment;
import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.Tweet;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.mapper.CommentMapper;
import com.soa.springcloud.mapper.TweetMapper;
import com.soa.springcloud.service.CommentService;
import com.soa.springcloud.service.TweetService;
//import com.soa.springcloud.service.UserFeignService;
import com.soa.springcloud.service.UserFeignService;
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

    @Resource
    private TweetService tweetService;

    @Resource
    private UserFeignService userService;

    @Override
    public JSONArray getComments(Integer tweetId){
        JSONArray jsonArray = new JSONArray();
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tweet_id",tweetId);
        List<Comment> list = commentMapper.selectList(queryWrapper);
        if(list!=null){
            for(int i=0;i<list.size();i++){
                JSONObject object = JSONUtil.parseObj(list.get(i));
                Integer unifiedId = list.get(i).getUnifiedId();
                //object.put("simpleUserInfo",tweetService.getSimpleUserInfo(object.getInt("unifiedId")));
                CommonResult<User> userResult = userService.UserById(unifiedId);
                User data = userResult.getData();
                data.setPassword(null);
                data.setUserName(null);
                data.setEmail(null);
                object.put("simpleUserInfo",data);
                jsonArray.add(object);
            }
        }
        return jsonArray;
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
