package com.soa.springcloud.entities;
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


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}