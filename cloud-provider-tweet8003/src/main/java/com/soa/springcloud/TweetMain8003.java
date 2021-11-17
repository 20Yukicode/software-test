package com.soa.springcloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@MapperScan("com.soa.springcloud.mapper")
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class TweetMain8003 {
    public static void main(String[] args) {
        SpringApplication.run(TweetMain8003.class,args);
    }
}
