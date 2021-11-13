package com.soa.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 工作经历
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobExperience {
    //用户统一id
    private int unified_id;
    //工作经历顺序id
    private int num_id;
    //开始时间
    private Date start_time;
    //结束时间
    private  Date end_time;
    //公司名称
    private String enterprise_name;
    //职位类型
    private  String position_type;
    //简介
    private String description;
}
