package com.soa.springcloud.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 统一id（自增）
     */
    @TableId(type = IdType.AUTO)
    private Integer unifiedId;

    /**
     * 用户名(唯一，代码内处理)
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 用户类型（企业0用户1）
     */
    private Integer userType;

    /**
     * 真实用户or企业姓名
     */
    private String trueName;

    /**
     * 头像url
     */
    private String pictureUrl;

    /**
     * 订阅数
     */
    private Integer subscribeNum;

    /**
     * 简短介绍
     */
    private String briefInfo;
    /**
     * 背景图片
     */
    private String background;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}