package com.soa.springcloud.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName picture
 */
@TableName(value ="picture")
@Data
public class Picture implements Serializable {
    /**
     * 动态id
     */
    @TableId
    private Integer tweetId;

    /**
     * 该动态的图片顺序id
     */
    @TableId
    private Integer numId;

    /**
     * 图片url
     */
    private String pictureUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}