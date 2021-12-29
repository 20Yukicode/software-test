package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.Resume;
import com.soa.springcloud.mapper.ResumeMapper;
import com.soa.springcloud.service.ResumeService;
import com.soa.springcloud.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RefreshScope
@Service
@Slf4j
public class ResumeServiceImpl implements ResumeService {

    private ResumeMapper resumeMapper;
//    private static String localPath;
//    private static String webPath;


    private static String endpoint;

    private static String accessKeyId;

    private static String accessKeySecret;

    private static String bucketName;

    @Value("${file.endpoint}")
    public void setEndpoint(String endpoint) {
        ResumeServiceImpl.endpoint = endpoint;
    }

    @Value("${file.accessKeyId}")
    public void setAccessKeyId(String accessKeyId) {
        ResumeServiceImpl.accessKeyId = accessKeyId;
    }

    @Value("${file.accessKeySecret}")
    public void setAccessKeySecret(String accessKeySecret) {
        ResumeServiceImpl.accessKeySecret = accessKeySecret;
    }

    @Value("${file.bucketName}")
    public void setBucketName(String bucketName) {
        ResumeServiceImpl.bucketName = bucketName;
    }

    @Autowired
    public void setEduExperienceMapper(ResumeMapper resumeMapper) {
        this.resumeMapper = resumeMapper;
    }

    @Override
    public List<Resume> getResume(Integer unifiedId) {
        QueryWrapper<Resume> wrapper=new QueryWrapper<>();
        wrapper.eq("unified_id",unifiedId);
        return resumeMapper.selectList(wrapper);
    }

    @Override
    public int addResume(Integer unifiedId,MultipartFile file) throws IOException {
        int resumeId=1;
        int result=0;
        Integer temp=resumeMapper.maxResumeId(unifiedId);
        if(temp==null);
        else resumeId=temp+1;
        //url存入数据库
        //String url=webPath+"/resume/"+unifiedId+"/"+resumeId+"/"+file.getOriginalFilename();
        String url="https://"+bucketName+"."+endpoint+"/"+unifiedId+"/"+resumeId+"/"+file.getOriginalFilename();
        Resume resume = new Resume(unifiedId,resumeId,file.getOriginalFilename(),url);
        result=resumeMapper.insert(resume);
        //存储文件

        //本地存储路径
        //String path = localPath+"\\resume\\"+unifiedId+"\\"+resumeId;
        String path = "resume/"+unifiedId+"/"+resumeId;
        FileUtils.saveUrl(file,path);

        //成功返回大于1
        return result;
    }

    @Override
    public int deleteResume(Integer unifiedId,Integer resumeId) {
        QueryWrapper<Resume> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("unified_id",unifiedId).eq("resume_id",resumeId);
        return resumeMapper.delete(queryWrapper);
    }
}
