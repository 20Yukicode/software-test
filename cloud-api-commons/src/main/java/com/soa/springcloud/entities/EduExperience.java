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
 * @TableName edu_experience
 */
@TableName(value ="edu_experience")
@Data
public class EduExperience implements Serializable {
    /**
     * 统一id
     */
    @TableId
    private Integer unifiedId;

    /**
     * 用户教育经历顺序id
     */
    @TableId
    private Integer numId;

    /**
     * 专业
     */
    private String major;

    /**
     * 学位
     */
    private String degree;

    /**
     * 学校名
     */
    private String collegeName;

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