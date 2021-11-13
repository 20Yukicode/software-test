package com.soa.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * 教育经历
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduExperience {
    //用户统一id
    private int unified_id;
    //教育经历顺序id
    private int num_id;
    //开始时间
    private Date start_time;
    //结束时间
    private  Date end_time;
    //学校名称
    private String college_name;
    //学位、教育层次
    private  String degree;
    //专业
    private String major;
}
