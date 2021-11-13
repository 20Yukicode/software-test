package com.soa.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 验证码
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Captcha {
    //邮箱
    private int email;
    //验证码
    private  String captcha;
    //产生时间
    private Date record_time;
}
