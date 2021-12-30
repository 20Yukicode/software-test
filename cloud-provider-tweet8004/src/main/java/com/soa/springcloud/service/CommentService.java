package com.soa.springcloud.service;

import cn.hutool.json.JSONArray;
import com.soa.springcloud.entities.Comment;

public interface CommentService {
    JSONArray getComments(Integer tweetId);
    int postComment(Comment comment);
    int deleteComment(Integer unifiedId,Integer tweetId,Integer floor);
}
