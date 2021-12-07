package com.soa.springcloud.entities;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName likes
 */
@TableName(value ="likes")
@Data
public class Likes implements Serializable {
    /**
     * 给该动态点赞的用户统一id
     */
    @TableId
    private Integer unifiedId;

    /**
     * 动态id
     */
    @TableId
    private Integer tweetId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}