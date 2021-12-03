package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.JobExperience;
import com.soa.springcloud.mapper.JobExperienceMapper;
import com.soa.springcloud.service.JobExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobExperienceServiceImpl implements JobExperienceService {

    private JobExperienceMapper jobExperienceMapper;

    @Autowired
    public void setJobExperienceMapper(JobExperienceMapper jobExperienceMapper) {
        this.jobExperienceMapper = jobExperienceMapper;
    }
    @Override
    public int postJobExperience(JobExperience jobExperience){
        //调用了sql语句
        Integer temp=jobExperienceMapper.maxNumberId(jobExperience.getUnifiedId());
        int max=0;
        if(temp==null)
            max=0;
        else
            max=temp+1;
        jobExperience.setNumId(max);
        return jobExperienceMapper.insert(jobExperience);
    }

    @Override
    public int deleteJobExperience(Integer unifiedId, Integer numId) {
        QueryWrapper<JobExperience> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("unified_id",unifiedId).eq("num_id",numId);
        return jobExperienceMapper.delete(queryWrapper);
    }

    @Override
    public int putJobExperience(JobExperience jobExperience) {
        QueryWrapper<JobExperience> wrapper=new QueryWrapper<>();
        wrapper.eq("unified_id", jobExperience.getUnifiedId()).eq("num_id",jobExperience.getNumId());
        return jobExperienceMapper.update(jobExperience,wrapper);
    }

    @Override
    public List<JobExperience> getJobExperience(Integer unifiedId) {
        QueryWrapper<JobExperience> wrapper=new QueryWrapper<>();
        wrapper.eq("unified_id",unifiedId);
        return jobExperienceMapper.selectList(wrapper);
    }
}
