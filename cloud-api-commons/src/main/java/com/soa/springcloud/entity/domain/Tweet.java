package com.soa.springcloud.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName tweet
 */
@TableName(value ="tweet")
@Data
public class Tweet implements Serializable {
    /**
     * 动态id(自增)
     */
    @TableId(type = IdType.AUTO)
    private Integer tweetId;

    /**
     * 内容
     */
    private String contents;

    /**
     * 动态可用状态（0可用1删除）
     */
    private Integer state;

    /**
     * 点赞数
     */
    private Integer praiseNum;

    /**
     * 动态产生时间
     */
    private Date recordTime;

    /**
     * 动态发送者统一id
     */
    private Integer unifiedId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}