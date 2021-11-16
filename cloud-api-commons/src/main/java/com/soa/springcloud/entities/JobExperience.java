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
 * @TableName job_experience
 */
@TableName(value ="job_experience")
@Data
public class JobExperience implements Serializable {
    /**
     * 统一id
     */
    @TableId
    private Integer unifiedId;

    /**
     * 用户工作经历顺序id
     */
    @TableId
    private Integer numId;

    /**
     * 简介
     */
    private String description;

    /**
     * 职位类型
     */
    private String positionType;

    /**
     * 公司名称
     */
    private String enterpriseName;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 开始时间
     */
    private Date startTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}