package com.soa.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 聊天记录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRecord {
    //发送者统一id
    private int unified_id1;
    //接收者统一id
    private  int unified_id2;
    //逻辑时间id
    private int time_id;
    //产生时间
    private Date record_time;
    //内容
    private String contents;
}
