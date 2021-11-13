package com.soa.springcloud.entities;

import com.sun.deploy.panel.ITreeNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 评论
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    //评论所属动态id
    private int tweet_id;
    //发送者统一认证id
    private int unified_id;
    //评论的顺序id
    private int comment_id;
    //产生时间
    private Date record_time;
    //内容
    private String contents;
}
