package com.soa.springcloud.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName resume
 */
@TableName(value ="resume")
@Data
public class Resume implements Serializable {
    /**
     * 统一id
     */
    @TableId
    private Integer unifiedId;

    /**
     * 该用户的简历顺序id
     */
    @TableId
    private Integer resumeId;

    /**
     * 文档url
     */
    private String documentUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}