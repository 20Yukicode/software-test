package com.soa.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订阅关系
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    //订阅者的统一id
    private int unified_id;
    //被订阅者的统一id
    private int subscribe_id;
}
