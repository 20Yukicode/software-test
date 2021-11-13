package com.soa.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    //用户统一id
    private int unified_id;
    //真实姓名
    private String true_name;
    //性别
    private String gender;
    //手机号码
    private String phone_num;
    //身份证
    private String id_card;
    //居住地
    private String live_place;
    //年龄
    private int age;
    //职位偏好
    private String pre_position;
    //头像图片流
    private String picture_string;
}
