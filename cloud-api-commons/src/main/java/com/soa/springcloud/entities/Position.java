package com.soa.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 职位
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {
    //企业统一认证id
    private int unified_id;
    //该岗位在企业内部的顺序id
    private int job_id;
    //职位类型
    private String position_type;
    //性别限制
    private String sex_limit;
    //联系方式
    private String contact_way;
    //工作地点
    private String work_place;
    //薪资
    private int reward;
    //简介
    private String description;
    //是否可用状态（用于惰性删除）
    private int state;
}
