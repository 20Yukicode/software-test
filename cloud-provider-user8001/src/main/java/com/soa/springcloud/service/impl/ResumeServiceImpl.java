package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.JobExperience;
import com.soa.springcloud.entities.Resume;
import com.soa.springcloud.mapper.EduExperienceMapper;
import com.soa.springcloud.mapper.ResumeMapper;
import com.soa.springcloud.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class ResumeServiceImpl implements ResumeService {

    private ResumeMapper resumeMapper;

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
    public int addResume() {

        return 0;
    }

    @Override
    public int deleteResume(Integer unifiedId,Integer resumeId) {
        QueryWrapper<Resume> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("unified_id",unifiedId).eq("ResumeId",resumeId);
        return resumeMapper.delete(queryWrapper);
        //删除本机文件！！！！！
    }


}
