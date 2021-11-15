package com.soa.springcloud.entity.domain;
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
     * 用户名(唯一)
     */
    @TableId
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}