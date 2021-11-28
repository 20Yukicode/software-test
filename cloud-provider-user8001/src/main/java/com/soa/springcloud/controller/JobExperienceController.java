package com.soa.springcloud.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.EnterpriseInfo;
import com.soa.springcloud.service.JobExperienceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class JobExperienceController {
    private final JobExperienceService jobExperienceService;

    public JobExperienceController(JobExperienceService jobExperienceService) {
        this.jobExperienceService = jobExperienceService;
    }

    /**
     * 增加教育经历
     * @param unified_id
     * @param start_time
     * @param end_time
     * @param enterprise_name
     * @param position_type
     * @param description
     * @return
     */
    @PostMapping("/user/job")
    public CommonResult postEduExperience(@RequestParam("uid") Integer unified_id,
                                          @RequestParam("stime") String start_time,
                                          @RequestParam("etime") String end_time,
                                          @RequestParam("name") String enterprise_name,
                                          @RequestParam("position") String position_type,
                                          @RequestParam("description") String description
    ) {
        //预处理传入参数
        if(unified_id==null){
            return CommonResult.failure("失败，uid空");
        }
        //开始插入数据
        if(jobExperienceService.postJobExperience(unified_id,start_time,end_time,enterprise_name,position_type,description)>0) {
            return CommonResult.success("增加工作经历成功");
        }
        return CommonResult.failure("插入失败");
    }

    /**
     * 删除工作经历
     * @param unified_id
     * @param num_id
     * @return
     */
    @DeleteMapping("/user/job")
    public CommonResult deleteEduExperience(@RequestParam("uid") Integer unified_id,
                                            @RequestParam("sid") Integer num_id) {
        if(unified_id==null){
            return CommonResult.failure("失败，uid空");
        }
        else if(num_id==null){
            return CommonResult.failure("失败，sid空");
        }
        if(jobExperienceService.deleteJobExperience(unified_id,num_id)>0) {
            return CommonResult.success("删除工作经历成功");
        }
        return CommonResult.failure("删除失败");
    }


    @PutMapping("/user/job")
    public CommonResult putEduExperience(@RequestParam("uid") Integer unified_id,
                                          @RequestParam("sid") Integer num_id,
                                          @RequestParam("stime") String start_time,
                                          @RequestParam("etime") String end_time,
                                          @RequestParam("name") String enterprise_name,
                                          @RequestParam("position") String position_type,
                                          @RequestParam("description") String description
    ) {
        //预处理传入参数
        if(unified_id==null){
            return CommonResult.failure("失败，uid空");
        }
        //开始更新数据
        if(jobExperienceService.putJobExperience(unified_id,num_id,start_time,end_time,enterprise_name,position_type,description)>0) {
            return CommonResult.success("更新工作经历成功");
        }
        return CommonResult.failure("更新失败");
    }

    @GetMapping("/user/job")
    public CommonResult getEduExperience(@RequestParam("uid") Integer unified_id){
        //预处理传入参数
        if(unified_id==null){
            return CommonResult.failure("失败，uid空");
        }
        //开始查询数据
        if(jobExperienceService.getJobExperience(unified_id)>0) {
            return CommonResult.success("查询工作经历成功");
        }
        return CommonResult.failure("查询失败");
    }
}
