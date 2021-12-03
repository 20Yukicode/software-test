package com.soa.springcloud.service;

import com.soa.springcloud.entities.Resume;
import java.util.List;

public interface ResumeService {
    List<Resume> getResume(Integer unifiedId);
    int addResume();
    int deleteResume();
}
