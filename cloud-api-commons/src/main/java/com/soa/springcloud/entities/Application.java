package com.soa.springcloud.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName application
 */
@TableName(value ="application")
@Data
public class Application implements Serializable {
    /**
     * 用户统一id
     */
    @TableId
    private Integer userId;

    /**
     * 用户简历顺序编号
     */
    @TableId
    private Integer resumeId;

    /**
     * 企业统一id
     */
    @TableId
    private Integer enterpriseId;

    /**
     * 企业岗位id
     */
    @TableId
    private Integer jobId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}