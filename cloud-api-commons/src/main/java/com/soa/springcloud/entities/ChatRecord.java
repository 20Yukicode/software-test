package com.soa.springcloud.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName chat_record
 */
@TableName(value ="chat_record")
@Data
public class ChatRecord implements Serializable {
    /**
     * 聊天顺序id（自增）
     */
    @TableId(type = IdType.AUTO)
    private Integer chatId;

    /**
     * 聊天内容
     */
    private String contents;

    /**
     * 产生时间
     */
    private Date recordTime;

    /**
     * 接收者统一id
     */
    private Integer unifiedId2;

    /**
     * 发送者统一id
     */
    private Integer unifiedId1;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}