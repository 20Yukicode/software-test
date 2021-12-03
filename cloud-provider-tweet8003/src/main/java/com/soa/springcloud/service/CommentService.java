package com.soa.springcloud.service;

import com.soa.springcloud.entities.Comment;

import java.util.List;

public interface CommentService {
    List getComments(Integer tweetId);
    int postComment(Comment comment);
    int deleteComment(Integer unifiedId,Integer tweetId,Integer floor);
}
