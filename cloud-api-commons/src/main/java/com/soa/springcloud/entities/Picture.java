package com.soa.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图片
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Picture {
    //动态id
    private int tweet_id;
    //图片顺序id
    private int num_id;
    //图片文件流
    private String picture_string;
}
