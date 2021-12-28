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
    private static String localPath;
    private static String webPath;

    @Resource
    private UserMapper userMapper;

    @Value("${file.localPath}")
    public void setLocalPath(String localPath) {
        HeadPictureServiceImpl.localPath = localPath;
    }
    @Value("${file.webPath}")
    public void setWebPath(String webPath) {
        HeadPictureServiceImpl.webPath = webPath;
    }

    @Override
    public int addHeadPicture(Integer unifiedId, MultipartFile file) throws IOException {
        int result=0;
        //路径存入数据库
        String url=webPath+"/headpic/"+unifiedId+"/"+file.getOriginalFilename();
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("unified_id",unifiedId);
        User user = new User();
        user.setPictureUrl(url);
        result=userMapper.update(user,queryWrapper);
        //开始存文件
        //本地存储路径
        String path = localPath+"\\headpic\\"+unifiedId;
        FileUtils.saveUrl(file,path);

        //成功返回大于1
        return result;
    }
}
