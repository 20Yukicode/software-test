package com.soa.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户（普通用户和企业用户的抽象父类）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    //用户id
    private int unified_id;
    //用户名（不可重复）
    private String user_name;
    //用户类型：0——企业，1——普通用户
    private int user_type;
    //邮箱
    private String email;
    //密码
    private String password;
}
