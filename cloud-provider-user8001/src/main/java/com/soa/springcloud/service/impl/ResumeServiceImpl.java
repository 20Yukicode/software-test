package com.soa.springcloud.service.impl;

import com.soa.springcloud.entities.Resume;
import com.soa.springcloud.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ResumeServiceImpl implements ResumeService {


    @Override
    public List<Resume> getResume(Integer unifiedId) {
        return null;
    }

    @Override
    public int addResume() {
        return 0;
    }

    @Override
    public int deleteResume() {
        return 0;
    }
}
