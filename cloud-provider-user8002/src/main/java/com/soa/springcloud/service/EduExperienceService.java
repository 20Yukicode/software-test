package com.soa.springcloud.service;

import com.soa.springcloud.entities.EduExperience;

import java.util.List;

public interface EduExperienceService {

    int postEduExperience(EduExperience eduExperience);
    int deleteEduExperience(Integer unifiedId,Integer numId);
    int putEduExperience(EduExperience eduExperience);
    List<EduExperience> getEduExperience(Integer unifiedId);
}
