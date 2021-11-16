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
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class Comment implements Serializable {
    /**
     * 动态顺序id
     */
    @TableId
    private Integer tweetId;

    /**
     * 楼层
     */
    @TableId
    private Integer floor;

    /**
     * 评论内容
     */
    private String contents;

    /**
     * 产生时间
     */
    private Date recordTime;

    /**
     * 发送者统一id
     */
    private Integer unifiedId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}