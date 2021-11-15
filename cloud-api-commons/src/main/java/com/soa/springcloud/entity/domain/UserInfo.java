package com.soa.springcloud.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user_info
 */
@TableName(value ="user_info")
@Data
public class UserInfo implements Serializable {
    /**
     * 统一id
     */
    @TableId(type = IdType.AUTO)
    private Integer unifiedId;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 职位偏好(只记录leve3级，即json文件最内层职位名字)
     */
    private String prePosition;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 居住地(-分隔省市eg.河北-石家庄)
     */
    private String livePlace;

    /**
     * 电话号码
     */
    private String phoneNum;

    /**
     * 性别（男、女）
     */
    private String gender;

    /**
     * 真实姓名
     */
    private String trueName;

    /**
     * 头像url
     */
    private String pictureUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}