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
import com.soa.springcloud.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
    @PostMapping(value = "/user/register")
    public CommonResult<Integer> create(@RequestBody User user)
    {
        //验证用户名是否重复
        if(userService.getUserByName(user.getUserName())!=null){
            return CommonResult.failure("用户名重复");
        }
        int result = userService.create(user);
        //返回生成用户的unifiedId
        Integer unifiedId = user.getUnifiedId();
        //创建User表时顺便创建对应的Info表

        if(Objects.equals(user.getUserType(), "user")){
            log.info("向userInfo插入数据");
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
     */
    @PostMapping("/user/email")
    public CommonResult<String> getMailCaptcha(@RequestParam("mail") String mail) {
        return CommonResult.success(mailService.sendMail(mail));
    }

    /**
     * 登录
     * @return
     */
    @PostMapping("/user/login")
    public CommonResult<JSON> login(@RequestBody User user,
                                    HttpServletResponse response) throws Exception {
        JSON json = new JSONObject();
        json.putByPath("unifiedId",0);
        json.putByPath("userType",0);
        //验证用户名是否存在
        User myUser = userService.getUserByName(user.getUserName());
        if(myUser==null){
            return CommonResult.failure("用户名不存在");
        }
        json.putByPath("userType",myUser.getUserType());
        //验证密码是否正确
        if(user.getPassword().equals(myUser.getPassword())) {
            json.putByPath("unifiedId",myUser.getUnifiedId());
            Integer unifiedId = myUser.getUnifiedId();
            //生成token
            com.alibaba.fastjson.JSONObject subject = new com.alibaba.fastjson.JSONObject(true);
            subject.put("unifiedId", unifiedId);
            subject.put("userName",user.getUserName() );
            String token = JWTUtils.createJWT("1", subject.toJSONString(), 60 * 60 * 24);//10秒过期
            //生成cookie
            Cookie cookie = new Cookie("token",token);
            cookie.setMaxAge(60 * 60 * 24);
            cookie.setPath ("/");
            response.addCookie(cookie);
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
        //log.info("***查询结果：" + user);
        if(user==null)return CommonResult.failure("用户不存在");
        return CommonResult.success(user);
    }

    @PutMapping("/user")
    public CommonResult<Integer> updateUser(@RequestBody User user) {
        int update = userService.update(user);
        if(update==0)return CommonResult.failure("更新失败");
        return CommonResult.success(update);
    }

    @PostMapping("/user/back")
    public CommonResult<Integer> updateUser(@RequestParam("unifiedId") Integer unifiedId,
                                            @RequestPart("file") MultipartFile file) throws IOException {
        //预处理传入参数
        if(unifiedId ==null){
            return CommonResult.failure("失败，unifiedId空");
        }
        //开始添加背景图片
        int i = userService.addBack(unifiedId,file);
        if(i >0) {
            return CommonResult.success("添加背景图片成功",null);
        }
        return CommonResult.failure("添加背景图片失败");
    }

}
