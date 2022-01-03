package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.mapper.UserMapper;
import com.soa.springcloud.service.HeadPictureService;
import com.soa.springcloud.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Service
@Slf4j
public class HeadPictureServiceImpl implements HeadPictureService {
    private static final String endpoint = "oss-cn-shanghai.aliyuncs.com";

    private static final String accessKeyId = "LTAI5tBG4652Uuc6Ljxi5hpu";

    private static final String accessKeySecret = "1SeLabxEsZdAPlRHN2kPkPzri3sxYi";

    private static final String bucketName = "soa-user-resume";
    @Resource
    private UserMapper userMapper;

    @Override
    public int addHeadPicture(Integer unifiedId, MultipartFile file) throws IOException {
        int result=0;
        //路径存入数据库
        //String url=webPath+"/headpic/"+unifiedId+"/"+file.getOriginalFilename();
        String url="https://"+bucketName+"."+endpoint+"/headpic/"+unifiedId+"/"+file.getOriginalFilename();
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("unified_id",unifiedId);
        User user = new User();
        user.setPictureUrl(url);
        result=userMapper.update(user,queryWrapper);
        //开始存文件
        //本地存储路径
        //String path = localPath+"\\headpic\\"+unifiedId;
        String path = "headpic/"+unifiedId;
        FileUtils.saveUrl(file,path);

        //成功返回大于1
        return result;
    }
}
