package com.soa.springcloud.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.mapper.UserMapper;
import com.soa.springcloud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: PaymentServiceImpl
 * @description:
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    //=====服务熔断
    @HystrixCommand(fallbackMethod = "userCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),// 失败率达到多少后跳闸
    })
    public int create(User user) {
        return userMapper.insert(user);
    }
    public int userCircuitBreaker_fallback(User user) {
        log.info("what？竟然出bug了！请稍后再试，/(ㄒoㄒ)/~~");
        return -1;
    }
    @Override
    public User getUserById(int unified_id) {
        return userMapper.selectById(unified_id);
    }
    @Override
    public User getUserByName(String user_name) {
        QueryWrapper<User>wrapper=new QueryWrapper<>();
        wrapper.eq("user_name",user_name);
        return userMapper.selectOne(wrapper);
    }

    public int update(User user){
        return userMapper.updateById(user);
    }

}
