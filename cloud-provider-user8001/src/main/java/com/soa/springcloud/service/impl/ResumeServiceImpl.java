package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.Resume;
import com.soa.springcloud.mapper.ResumeMapper;
import com.soa.springcloud.service.ResumeService;
import com.soa.springcloud.util.PictureUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ResumeServiceImpl implements ResumeService {

    private ResumeMapper resumeMapper;
    private static String localPath;
    private static String webPath;


    @Value("${file.localPath}")
    public void setLocalPath(String localPath) {
        ResumeServiceImpl.localPath = localPath;
    }
    @Value("${file.webPath}")
    public void setWebPath(String webPath) {
        ResumeServiceImpl.webPath = webPath;
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
        String url=webPath+"/resume/"+unifiedId+"/"+resumeId+"/"+file.getOriginalFilename();
        Resume resume = new Resume(unifiedId,resumeId,file.getOriginalFilename(),url);
        result=resumeMapper.insert(resume);
        //存储文件

        //本地存储路径
        String path = localPath+"\\resume\\"+unifiedId+"\\"+resumeId;
        PictureUtils.saveUrl(file,path);

        //成功返回大于1
        return result;
    }

    @Override
    public int deleteResume(Integer unifiedId,Integer resumeId) {
        QueryWrapper<Resume> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("unified_id",unifiedId).eq("ResumeId",resumeId);
        return resumeMapper.delete(queryWrapper);
        //删除本机文件！！！！！


    }


}
