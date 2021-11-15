package com.soa.springcloud.entity.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName captcha
 */
@TableName(value ="captcha")
@Data
public class Captcha implements Serializable {
    /**
     * 电子邮箱
     */
    @TableId
    private String email;

    /**
     * 验证码发送时间
     */
    private Date recordTime;

    /**
     * 验证码
     */
    private String captcha;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}