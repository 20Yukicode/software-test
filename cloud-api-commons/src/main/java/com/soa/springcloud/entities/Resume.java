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
 * @TableName resume
 */
@TableName(value ="resume")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
     * 简历文件名
     */
    private String resumeName;

    /**
     * 文档url
     */
    private String documentUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}