package com.soa.springcloud.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.thymeleaf.TemplateEngine;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MailService {
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private TemplateEngine templateEngine;

    @Resource
    private UserMapper userMapper;
    public String sendMail(String mail) throws MessagingException {
        List<User> users = userMapper.selectByMap(null);
        for(User one :users){
            //若邮箱已经被注册过了
            if(mail.equals(one.getEmail())){
                return "failure";
            }
        }
        //生成随机激活码
        String captcha = RandomUtil.randomStringUpper(8);
        //生成邮件模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        Context context = new Context();
        context.setVariable("email",mail);
        context.setVariable("captcha",captcha);
        context.setVariable("createTime",sdf.format(new Date()));
        String templateContent = templateEngine.process("registerMail", context);
        //发送邮件
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("soa8001@163.com");
        helper.setTo(mail);
        helper.setSubject("LinkedOut注册激活码");
        helper.setText(templateContent, true);
//        message.setFrom("soa8001@163.com");
//        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
//        message.setSubject("激活码");
//        message.setText(templateContent);
        mailSender.send(message);
        return captcha;

    }

}
