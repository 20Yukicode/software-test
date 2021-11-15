package com.soa.springcloud.entity.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName position
 */
@TableName(value ="position")
@Data
public class Position implements Serializable {
    /**
     * 统一id
     */
    @TableId
    private Integer unifiedId;

    /**
     * 工作名称
     */
    @TableId
    private String jobName;

    /**
     * 图片url
     */
    private String pictureUrl;

    /**
     * 可用状态（0可用1删除）
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
     * 各种限制
     */
    private String limitation;

    /**
     * 职位类型(只记录leve3级，即json文件最内层职位名字)
     */
    private String positionType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}