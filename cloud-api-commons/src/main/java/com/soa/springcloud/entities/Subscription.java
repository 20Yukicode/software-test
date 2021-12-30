package com.soa.springcloud.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName subscription
 */
@NoArgsConstructor
@TableName(value ="subscription")
@Data
public class Subscription implements Serializable {
    /**
     * 订阅者的统一id
     */
    @TableId
    private Integer unifiedId;

    /**
     * 被订阅者的统一id
     */
    @TableId
    private Integer subscribeId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}