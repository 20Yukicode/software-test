package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.EduExperience;
import com.soa.springcloud.mapper.EduExperienceMapper;
import com.soa.springcloud.service.EduExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EduExperienceServiceImpl implements EduExperienceService {
    private EduExperienceMapper eduExperienceMapper;

    @Autowired
    public void setEduExperienceMapper(EduExperienceMapper eduExperienceMapper) {
        this.eduExperienceMapper = eduExperienceMapper;
    }

    @Override
    public int postEduExperience(EduExperience eduExperience) {
        //调用了sql语句
        Integer temp=eduExperienceMapper.maxNumberId(eduExperience.getUnifiedId());
        int max=0;
        if(temp==null)
            max=0;
        else
            max=temp+1;
        eduExperience.setNumId(max);
        return eduExperienceMapper.insert(eduExperience);
    }

    @Override
    public int deleteEduExperience(Integer unifiedId, Integer numId) {
        QueryWrapper<EduExperience> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("unified_id",unifiedId).eq("num_id",numId);
        return eduExperienceMapper.delete(queryWrapper);
    }

    @Override
    public int putEduExperience(EduExperience eduExperience) {
        QueryWrapper<EduExperience> wrapper=new QueryWrapper<>();
        wrapper.eq("unified_id", eduExperience.getUnifiedId()).eq("num_id",eduExperience.getNumId());
        return eduExperienceMapper.update(eduExperience,wrapper);
    }

    @Override
    public List<EduExperience> getEduExperience(Integer unifiedId) {
        QueryWrapper<EduExperience> wrapper=new QueryWrapper<>();
        wrapper.eq("unified_id",unifiedId);
        return eduExperienceMapper.selectList(wrapper);
    }
}
