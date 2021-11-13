package com.soa.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 点赞
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Praise {
    //点赞者统一id
    private int unified_id;
    //被点赞动态id
    private int tweet_id;
}
