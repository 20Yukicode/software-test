package com.soa.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 企业信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseInfo {
    //用户统一id
    private int unified_id;
    //真实姓名
    private String true_name;
    //联系方式
    private String contact_way;
    //简介
    private String contents;
    //头像图片流
    private String picture_string;
}
