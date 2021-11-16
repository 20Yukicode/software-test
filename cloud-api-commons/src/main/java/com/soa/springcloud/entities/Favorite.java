package com.soa.springcloud.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName favorite
 */
@TableName(value ="favorite")
@Data
public class Favorite implements Serializable {
    /**
     * 用户统一id
     */
    @TableId
    private Integer userId;

    /**
     * 企业统一id
     */
    @TableId
    private Integer enterpriseId;

    /**
     * 工作名称
     */
    @TableId
    private String jobName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}