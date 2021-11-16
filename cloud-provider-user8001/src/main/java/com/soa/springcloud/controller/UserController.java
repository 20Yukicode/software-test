package com.soa.springcloud.controller;

import com.soa.springcloud.entity.domain.User;
import com.soa.springcloud.service.EnterpriseInfoService;
import com.soa.springcloud.service.impl.UserInfoServiceImpl;
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
    private UserInfoServiceImpl userInfoService;
    @Resource
    private EnterpriseInfoService enterpriseInfoService;
    @Resource
    private DiscoveryClient discoveryClient;

    /**
     * 服务发现
     * @return
     */
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

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping(value = "/user/register")
    public int create(@RequestBody User user)
    {
        //验证用户名是否重复
        if(userService.getUserByName(user.getUserName())!=null){
            return 0;
        }
        int result = userService.create(user);
        //log.info("*****id：" + user.getUnified_id());
        //返回生成用户的unified_id
        Integer unifiedId = user.getUnifiedId();
        //创建User表时顺便创建对应的Info表
        log.info("user："+user);
        log.info("id："+user.getUnifiedId());
        if(user.getUserType()==1){
            log.info("向userinfo插入数据");
            userInfoService.create(unifiedId);
        }
        //若为企业类型，则建立EnterpriseInfo表
        else enterpriseInfoService.create(unifiedId);
        //直接返回用户对应的id
        return unifiedId;
    }


    /**
     * 登录
     * @param user_name
     * @param password
     * @return
     */
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

}
