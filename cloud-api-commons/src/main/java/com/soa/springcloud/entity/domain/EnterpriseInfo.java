package com.soa.springcloud.entity.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName enterprise_info
 */
@TableName(value ="enterprise_info")
@Data
public class EnterpriseInfo implements Serializable {
    /**
     * 统一id
     */
    @TableId
    private Integer unifiedId;

    /**
     * 联系方式
     */
    private String contactWay;

    /**
     * 简介
     */
    private String description;

    /**
     * 真实名称
     */
    private String trueName;

    /**
     * 头像url
     */
    private String pictureUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}