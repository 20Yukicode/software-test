package com.soa.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 简历
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resume {
    //用户统一id
    private int unified_id;
    //该用户的简历顺序id
    private int resume_id;
    //简历文件流
    private String document;
}
