package com.soa.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 动态
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tweet {
    //动态id
    private  int tweet_id;
    //发送者统一认证id
    private int unified_id;
    //发送时间
    private Date record_time;
    //点赞总数
    private int praise_num;
    //内容
    private String contents;
    //是否可用状态（用于惰性删除）
    private int state;
}
