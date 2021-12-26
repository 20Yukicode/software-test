package com.soa.springcloud.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.Position;
import com.soa.springcloud.entities.Application;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.entities.Resume;
import com.soa.springcloud.entities.UserInfo;
import com.soa.springcloud.mapper.ApplicationMapper;
import com.soa.springcloud.mapper.PositionMapper;
import com.soa.springcloud.mapper.UserMapper;
import com.soa.springcloud.mapper.UserInfoMapper;
import com.soa.springcloud.mapper.ResumeMapper;
import com.soa.springcloud.dto.ApplicantInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class UserRecruitmentServiceImpl{

    @Resource
    private ApplicationMapper applicationMapper;

    @Resource
    private PositionMapper positionMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ResumeMapper resumeMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private EnterprisePositionServiceImpl enterprisePositionService;


    public int sendApplication(Application application) {
        return applicationMapper.insert(application);
    }

    public int cancelApplication(Application application) {
        QueryWrapper<Application> wrapper=new QueryWrapper<>();
        wrapper.eq("job_id",application.getJobId());
        wrapper.eq("enterprise_id",application.getEnterpriseId());
        wrapper.eq("user_id",application.getUserId());
        return applicationMapper.delete(wrapper);//执行删除操作
    }


    public List<Position> getAppliedPositions(int userId) {
        QueryWrapper<Application> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<Application> applications=applicationMapper.selectList(wrapper);//找到所有的请求
        List<Position> positions=new ArrayList<Position>();//初始化列表
        for(Application application :applications){
            Position p= enterprisePositionService.getPositionInfo(application.getEnterpriseId(),application.getJobId());
            if(p!=null)positions.add(p);
        }
        if(positions.isEmpty())return null;//是空没找到
        return positions;
    }

    //需要uerinfo的信息，可以作为跨模块调用的尝试，这里先直接写
    public List<ApplicantInfoDto> getAllApplicants(int unifiedId, int jobId) {
        QueryWrapper<Application> wrapper=new QueryWrapper<>();
        wrapper.eq("enterprise_id",unifiedId);//注意是企业的id
        wrapper.eq("job_id",jobId);
        List<Application> applications=applicationMapper.selectList(wrapper);//找到所有的请求
        List<ApplicantInfoDto> applicants=new ArrayList<ApplicantInfoDto>();
        for(Application application :applications){
            User user=userMapper.selectById(application.getUserId());
            QueryWrapper<Resume> resumeQueryWrapper=new QueryWrapper<>();
            resumeQueryWrapper.eq("unified_id",application.getUserId());
            resumeQueryWrapper.eq("resume_id",application.getResumeId());
            Resume resume=resumeMapper.selectOne(resumeQueryWrapper);
            ApplicantInfoDto temp=new ApplicantInfoDto();
            temp.setUnifiedId(user.getUnifiedId());
            temp.setPictureUrl(user.getPictureUrl());
            temp.setBrief_info(user.getBriefInfo());
            temp.setResumeName(resume.getResumeName());
            temp.setResumeUrl(resume.getDocumentUrl());
            applicants.add(temp);
        }
        if(applicants.isEmpty())return null;
        return applicants;
    }
}
