package com.soa.springcloud.entities;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName praise
 */
@TableName(value ="praise")
@Data
public class Praise implements Serializable {
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