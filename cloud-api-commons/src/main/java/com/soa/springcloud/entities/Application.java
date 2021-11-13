package com.soa.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 职位申请
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    //申请该岗位的用户统一id
    private int user_id;
    //用户的简历id
    private int resume_id;
    //该岗位所属企业的统一id
    private int enterprise_id;
    //该岗位在企业内的顺序id
    private int job_id;
}
