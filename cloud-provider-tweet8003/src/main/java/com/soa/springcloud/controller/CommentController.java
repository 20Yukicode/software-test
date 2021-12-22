package com.soa.springcloud.controller;


import com.soa.springcloud.entities.Comment;
import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class CommentController {

    @Resource
    private CommentService commentService;

    @GetMapping("/tweet/comment/{tweetId}")
    public CommonResult getComments(@PathVariable Integer tweetId){
        if(tweetId == null)
            return CommonResult.failure("错误，tweetId为空");

        List comments = commentService.getComments(tweetId);

        return CommonResult.success("查询成功",comments);
    }

    @PostMapping("/tweet/comment")
    public CommonResult postComment(@RequestParam Integer unifiedId,
                                    @RequestParam Integer tweetId,
                                    @RequestParam String contents){
        if(unifiedId==null)
            return CommonResult.failure("错误，unifiedId为空");
        if(tweetId == null)
            return CommonResult.failure("错误，tweetId为空");
        Comment comment = new Comment();
        comment.setRecordTime(new Date());
        comment.setTweetId(tweetId);
        comment.setUnifiedId(unifiedId);
        comment.setContents(contents);
        if(commentService.postComment(comment)>0)
            return CommonResult.success("评论成功",null);

        return CommonResult.failure("评论失败");

    }
    @DeleteMapping("/tweet/comment")
    public CommonResult deleteComment(@RequestParam Integer unifiedId,
                                      @RequestParam Integer tweetId,
                                      @RequestParam Integer floor){
        if(unifiedId==null)
            return CommonResult.failure("错误，unifiedId为空");
        if(tweetId == null)
            return CommonResult.failure("错误，tweetId为空");

        if(floor == null)
            return CommonResult.failure("错误，floor为空");

        if(commentService.deleteComment(unifiedId,tweetId,floor)>0)
            return CommonResult.success("删除成功",null);

        return CommonResult.failure("删除失败");

    }
}
