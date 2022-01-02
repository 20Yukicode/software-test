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
 * @TableName position
 */
@TableName(value ="position")
@Data
public class Position implements Serializable {
    /**
     * 企业岗位id
     */
    @TableId(type = IdType.AUTO)
    private Integer jobId;

    /**
     * 图片url
     */
    private String pictureUrl;

    /**
     * 可用状态（0删除1可用）
     */
    private Integer state;

    /**
     * 简介
     */
    private String description;

    /**
     * 薪资
     */
    private String reward;

    /**
     * 工作地点(-分隔省市eg.河北-石家庄）
     */
    private String workPlace;

    /**
     * 联系方式
     */
    private String contactWay;

    /**
     * 各种岗位限制条件
     */
    private String limitation;

    /**
     * 职位类型(只记录leve3级，即json文件最内层职位名字)
     */
    private String positionType;

    /**
     * 工作名称
     */
    private String jobName;

    /**
     * 统一id
     */
    private Integer unifiedId;

    /**
     * 岗位发布时间
     */
    private Date recordTime;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}