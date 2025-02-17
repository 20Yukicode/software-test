package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.mapper.UserMapper;
import com.soa.springcloud.service.UserService;
import com.soa.springcloud.util.MD5Utils;
import com.soa.springcloud.util.FileUtils;
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

    private static final String endpoint = "oss-cn-shanghai.aliyuncs.com";

    private static final String bucketName = "soa-user-resume";

    @Override
    public int create(User user) {
        user.setPassword(MD5Utils.MD5(user.getPassword()));
        return userMapper.insert(user);
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
        //String url=webPath+"/back/"+unifiedId+"/"+file.getOriginalFilename();
        String url="https://"+bucketName+"."+endpoint+"/back/"+unifiedId+"/"+file.getOriginalFilename();
        User user= new User();
        user.setUnifiedId(unifiedId);
        user.setBackground(url);
        int result = userMapper.updateById(user);

        //存储文件
        //本地存储路径
        //String path = localPath+"\\back\\"+unifiedId;
        String path = "back/"+unifiedId;
        FileUtils.saveUrl(file,path);

        //成功返回大于1
        return result;
    }

}
