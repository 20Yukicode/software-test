package com.soa.springcloud.controller;
import com.soa.springcloud.entities.Application;
import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.Position;
import com.soa.springcloud.service.impl.UserRecruitmentServiceImpl;
import com.soa.springcloud.service.impl.EnterprisePositionServiceImpl;
import com.soa.springcloud.dto.ApplicantInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.soa.springcloud.dto.PositionDeleteDto;
import com.soa.springcloud.dto.PositionInfoDto;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
@RestController
@Slf4j
public class UserRecruitmentController {
    @Resource
    private EnterprisePositionServiceImpl enterprisePositionService;
    @Resource
    private UserRecruitmentServiceImpl userRecruitmentService;

    @PostMapping(value = "/recruit/application")
    public CommonResult apply(@RequestBody Application application){
        if(userRecruitmentService.sendApplication(application)==1)
            return CommonResult.success("发送成功",application);
        else
            return CommonResult.failure("发送失败",null);
    }

    @DeleteMapping(value = "/recruit/application")
    public CommonResult delete(@RequestBody Application application){
        if(userRecruitmentService.cancelApplication(application)==1)
            return CommonResult.success("取消成功",application);
        else
            return CommonResult.failure("取消失败",null);
    }

    @GetMapping(value = "/recruit/application")
    public CommonResult getAppliedPositions(@RequestParam int userId){
        List<Position> positions= userRecruitmentService.getAppliedPositions(userId);
        if(positions==null)
            return CommonResult.failure("该用户还没有投递过岗位",null);
        else
            return CommonResult.success("查找成功",positions);
    }

    @GetMapping(value = "/recruit/applicants")
    public CommonResult getAllApplicants(@RequestParam int unifiedId,
                                         @RequestParam int jobId){
        List<ApplicantInfoDto> applicants= userRecruitmentService.getAllApplicants(unifiedId,jobId);
        if(applicants==null)
            return CommonResult.failure("该岗位还没有申请者",null);
        else
            return CommonResult.success("查找成功",applicants);
    }
}
