package com.soa.springcloud.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.soa.springcloud.entities.Resume;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.mapper.UserMapper;
import com.soa.springcloud.service.UserService;
import com.soa.springcloud.util.PictureUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @ClassName: PaymentServiceImpl
 * @description:
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    private static String localPath;
    private static String webPath;

    @Value("${file.localPath}")
    public void setLocalPath(String localPath) {
        UserServiceImpl.localPath = localPath;
    }
    @Value("${file.webPath}")
    public void setWebPath(String webPath) {
        UserServiceImpl.webPath = webPath;
    }
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
    public User getUserById(int unifiedId) {
        return userMapper.selectById(unifiedId);
    }
    @Override
    public User getUserByName(String userName) {
        QueryWrapper<User>wrapper=new QueryWrapper<>();
        wrapper.eq("user_name",userName);
        return userMapper.selectOne(wrapper);
    }

    public int update(User user){
        return userMapper.updateById(user);
    }

    public int addBack(Integer unifiedId, MultipartFile file) throws IOException {

        //url存入数据库
        String url=webPath+"/back/"+unifiedId+"/"+file.getOriginalFilename();
        User user= new User();
        user.setUnifiedId(unifiedId);
        user.setBackground(url);
        int result = userMapper.updateById(user);

        //存储文件
        //本地存储路径
        String path = localPath+"\\back\\"+unifiedId;
        PictureUtils.saveUrl(file,path);

        //成功返回大于1
        return result;
    }

}
