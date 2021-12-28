package com.soa.springcloud.service;

import com.soa.springcloud.entities.JobExperience;
import java.util.List;

public interface JobExperienceService {
    int postJobExperience(JobExperience jobExperience);
    int deleteJobExperience(Integer unifiedId,Integer numId);
    int putJobExperience(JobExperience jobExperience);
    List<JobExperience> getJobExperience(Integer unifiedId);


}
