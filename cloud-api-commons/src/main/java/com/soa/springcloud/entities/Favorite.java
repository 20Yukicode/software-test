package com.soa.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 收藏夹
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {
    //用户统一id
    private int user_id;
    //企业统一id
    private int enterprise_id;
    //该岗位在企业内部的顺序id
    private int job_id;
}
