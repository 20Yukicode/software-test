package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.JobExperience;
import com.soa.springcloud.mapper.JobExperienceMapper;
import com.soa.springcloud.service.JobExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobExperienceImpl implements JobExperienceService {

    private JobExperienceMapper jobExperienceMapper;

    @Autowired
    public void setJobExperienceMapper(JobExperienceMapper jobExperienceMapper) {
        this.jobExperienceMapper = jobExperienceMapper;
    }
    //增没写完
    @Override
    public int postJobExperience(Integer unified_id,String stime,String etime,String name,String position,String description){
        int max=1;
        QueryWrapper<JobExperience> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("unified_id",unified_id);
        List<Object> objects = jobExperienceMapper.selectObjs(queryWrapper);

        return jobExperienceMapper.insert(new JobExperience());
    }

    @Override
    public int deleteJobExperience(Integer unified_id, Integer num_id) {
        QueryWrapper<JobExperience> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("unified_id",unified_id).eq("num_id",num_id);
        return jobExperienceMapper.delete(queryWrapper);
    }

    @Override
    public int putJobExperience(Integer unified_id, Integer num_id, String stime, String etime, String name, String position, String description) {
        return 0;
    }

    @Override
    public int getJobExperience(Integer unified_id) {
        return 0;
    }
}
