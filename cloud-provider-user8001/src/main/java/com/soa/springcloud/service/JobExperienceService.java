package com.soa.springcloud.service;

import org.springframework.web.bind.annotation.RequestParam;

public interface JobExperienceService {
    int postJobExperience(Integer unified_id,String stime,String etime,String name,String position,String description);
    int deleteJobExperience(Integer unified_id,Integer num_id);
    int putJobExperience(Integer unified_id,Integer num_id,String stime,String etime,String name,String position,String description);
    int getJobExperience(Integer unified_id);


}
