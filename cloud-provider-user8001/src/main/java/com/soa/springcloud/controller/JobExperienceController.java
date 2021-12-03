package com.soa.springcloud.controller;

import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.JobExperience;
import com.soa.springcloud.service.JobExperienceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Slf4j
public class JobExperienceController {
    private final JobExperienceService jobExperienceService;

    public JobExperienceController(JobExperienceService jobExperienceService) {
        this.jobExperienceService = jobExperienceService;
    }


    @PostMapping("/user/job")
    public CommonResult postEduExperience(@RequestBody JobExperience jobExperience) {
        //预处理传入参数
        if(jobExperience.getUnifiedId() ==null){
            return CommonResult.failure("失败，uid空");
        }
        //开始插入数据
        if(jobExperienceService.postJobExperience(jobExperience)>0) {
            return CommonResult.success("增加工作经历成功",null);
        }
        return CommonResult.failure("插入失败");
    }

    /**
     * 删除工作经历
     * @param unifiedId
     * @param numId
     * @return
     */
    @DeleteMapping("/user/job")
    public CommonResult deleteEduExperience(@RequestParam("uid") Integer unifiedId,
                                            @RequestParam("sid") Integer numId) {
        if(unifiedId==null){
            return CommonResult.failure("失败，uid空");
        }
        else if(numId==null){
            return CommonResult.failure("失败，sid空");
        }

        if(jobExperienceService.deleteJobExperience(unifiedId,numId)>0) {
            return CommonResult.success("删除工作经历成功");
        }
        return CommonResult.failure("删除失败");
    }

    /**
     * 更新用户工作经历
     */
    @PutMapping("/user/job")
    public CommonResult putEduExperience(@RequestBody JobExperience jobExperience){
        //预处理传入参数
        if(jobExperience.getUnifiedId()==null){
            return CommonResult.failure("失败，uid空");
        }
        //开始更新数据
        if(jobExperienceService.putJobExperience(jobExperience)>0) {
            return CommonResult.success("更新工作经历成功",null);
        }
        return CommonResult.failure("更新失败");
    }

    /**
     * 获取某用户工作经历
     * @param unifiedId
     * @return
     */
    @GetMapping("/user/job")
    public CommonResult getEduExperience(@RequestParam("uid") Integer unifiedId){
        //预处理传入参数
        if(unifiedId==null){
            return CommonResult.failure("失败，uid空");
        }
        //开始查询数据
        return CommonResult.success("查询工作经历成功",jobExperienceService.getJobExperience(unifiedId));
    }
}
