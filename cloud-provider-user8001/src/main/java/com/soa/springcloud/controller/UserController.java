package com.soa.springcloud.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.service.EnterpriseInfoService;
import com.soa.springcloud.service.TweetHystrixService;
import com.soa.springcloud.service.impl.MailService;
import com.soa.springcloud.service.impl.UserInfoServiceImpl;
import com.soa.springcloud.service.impl.UserServiceImpl;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    private MailService mailService;
    @Resource
    private UserServiceImpl userService;
    @Resource
    private UserInfoServiceImpl userInfoService;
    @Resource
    private EnterpriseInfoService enterpriseInfoService;
    @Resource
    private DiscoveryClient discoveryClient;
@Resource
private TweetHystrixService tweetHystrixService;
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
    @PostMapping(value = "/user")
    public CommonResult<Integer> create(@RequestBody User user,HttpServletRequest request)
    {
        //验证用户名是否重复
        if(userService.getUserByName(user.getUserName())!=null){
            return CommonResult.failure("用户名重复");
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
        return CommonResult.success(unifiedId);
    }

    /**
     *
     * @param mail
     * @return
     * @throws MessagingException
     */
    @PostMapping("/user/email")
    public CommonResult<String> getMailCaptcha(@RequestParam("mail") String mail) throws MessagingException {
        return CommonResult.success(mailService.sendMail(mail));
    }

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    @GetMapping("/user")
    public CommonResult<JSON> login(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpServletRequest request) {
        JSON json = new JSONObject();
        json.putByPath("unifiedId",0);
        json.putByPath("userType",0);
        //验证用户名是否存在
        User user = userService.getUserByName(userName);
        if(user==null){
            return CommonResult.failure("用户名不存在");
        }
        json.putByPath("userType",user.getUserType());
        //验证密码是否正确
        //这里有个坑，不能用==判断相等，因为两个字符串不是来自线程池的同一位置
        if(password.equals(user.getPassword())) {
            json.putByPath("unifiedId",user.getUnifiedId());
            return CommonResult.success(json);
        }
        //用户存在但密码错误
        return CommonResult.failure("密码错误");
    }

    /**
     * 根据id获取用户具体信息
     * @param unifiedId
     * @return
     */
    @GetMapping("/user/get/{unifiedId}")
    public CommonResult<User> getUserById(@PathVariable("unifiedId") int unifiedId) {
        User user = userService.getUserById(unifiedId);
        log.info("***查询结果：" + user);
        if(user==null)return CommonResult.failure("用户不存在");
        return CommonResult.success(user);
    }

    @PutMapping("/user")
    public CommonResult<Integer> updateUser(@RequestBody User user) {
        int update = userService.update(user);
        if(update==0)return CommonResult.failure("更新失败");
        return CommonResult.success(update);
    }

}
