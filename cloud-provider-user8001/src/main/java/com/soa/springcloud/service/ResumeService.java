package com.soa.springcloud.service;

import com.soa.springcloud.entities.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {
    List<Resume> getResume(Integer unifiedId);
    int addResume(Integer unifiedId,MultipartFile file);
    int deleteResume(Integer unifiedId,Integer resumeId);
}
