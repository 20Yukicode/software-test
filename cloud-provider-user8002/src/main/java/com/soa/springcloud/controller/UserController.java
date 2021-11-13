package com.soa.springcloud.controller;

import com.soa.springcloud.entities.User;
import com.soa.springcloud.entities.UserInfo;
import com.soa.springcloud.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: PaymentController
 * @description:
 **/
@RestController
@Slf4j
public class UserController {

    @Value("${server.port}")
    private String serverPort;//添加serverPort

    @Resource
    private UserServiceImpl userService;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/user/discovery")
    public Object discovery()
    {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("*****element: "+element);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-USER-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }

        return this.discoveryClient;
    }

    @PostMapping(value = "/user/register")
    public int create(@RequestBody User user)
    {
        //验证用户名是否重复
        if(userService.getUserByName(user.getUser_name())!=null){
            return 0;
        }
        int result = userService.create(user);
        log.info("*****插入结果：" + result);

        return 1;
    }

    @GetMapping("/user/login/{user_name}/{password}")
    public int login(@PathVariable("user_name") String user_name,@PathVariable("password") String password) {
        //验证用户名是否存在
        User user = userService.getUserByName(user_name);
        if(user==null){
            return 0;
        }
        //验证密码是否正确
        //这里有个坑，不能用==判断相等，因为两个字符串不是来自线程池的同一位置
        if(password.equals(user.getPassword()))return 1;
        //用户存在但密码错误
        return 2;
    }

    @GetMapping("/user/get/{unified_id}")
    public User getUserById(@PathVariable("unified_id") int unified_id) {
        User user = userService.getUserById(unified_id);
        log.info("***查询结果：" + user);
        return user;
    }
    @GetMapping("/user/info/{unified_id}")
    public UserInfo getUserInfo(@PathVariable("unified_id") int unified_id) {
        UserInfo userInfo = userService.getUserInfo(unified_id);
        log.info("***查询结果：" + userInfo);
        return userInfo;
    }
}
