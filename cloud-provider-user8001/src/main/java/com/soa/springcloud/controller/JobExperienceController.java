package com.soa.springcloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.soa.springcloud.api.CommonResult;
import com.soa.springcloud.entities.JobExperience;
import com.soa.springcloud.service.JobExperienceService;
import com.soa.springcloud.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@Slf4j
//@CrossOrigin(origins = "*")
public class JobExperienceController {
    private final JobExperienceService jobExperienceService;
    private final SearchService searchService;

    public JobExperienceController(JobExperienceService jobExperienceService, SearchService searchService) {
        this.jobExperienceService = jobExperienceService;
        this.searchService = searchService;
    }


    /**
     * 增加工作经历
     * @param jobExp
     * @return
     */
    @PostMapping("/user/job")
    public CommonResult postJobExperience(@RequestBody JSONObject jobExp){
        JobExperience jobExperience;
        try{
            //获取字符串中的时间（“格式：yyyy年mm月”）
            String st = (String) jobExp.get("startTime");
            jobExp.put("startTime",st.substring(0,4)+"-"+st.substring(5,7)+"-01");
            String et = (String) jobExp.get("endTime");
            jobExp.put("endTime",et.substring(0,4)+"-"+et.substring(5,7)+"-01");
            //强转对象
            jobExperience = (JobExperience)
                    com.alibaba.fastjson.JSONObject.toJavaObject(jobExp,JobExperience.class);
            //时区处理
            jobExperience.getStartTime().setHours(jobExperience.getStartTime().getHours()+8);
            jobExperience.getEndTime().setHours(jobExperience.getEndTime().getHours()+8);
        }
        catch (Exception e) {
            return CommonResult.failure("日期转换失败");
        }
        //预处理传入参数
        if(jobExperience.getUnifiedId() ==null){
            return CommonResult.failure("失败，unifiedId空");
        }
        //开始插入数据
        if(jobExperienceService.postJobExperience(jobExperience)>0) {
            return CommonResult.success("增加工作经历成功",null);
        }
        return CommonResult.failure("增加失败");
    }

    /**
     * 删除工作经历
     * @param unifiedId
     * @param numId
     * @return
     */
    @DeleteMapping("/user/job")
    public CommonResult deleteJobExperience(@RequestParam("unifiedId") Integer unifiedId,
                                            @RequestParam("numId") Integer numId) {
        if(unifiedId==null){
            return CommonResult.failure("失败，unifiedId空");
        }
        else if(numId==null){
            return CommonResult.failure("失败，numId空");
        }
        if(jobExperienceService.deleteJobExperience(unifiedId,numId)>0) {
            return CommonResult.success("删除工作经历成功",null);
        }
        return CommonResult.failure("删除失败");
    }

    /**
     * 更新工作经历
     * @param jobExp
     * @return
     */
    @PutMapping("/user/job")
    public CommonResult putJobExperience(@RequestBody JSONObject jobExp){
        JobExperience jobExperience;
        try{
            //获取字符串中的时间（“格式：yyyy年mm月”）
            String st = (String) jobExp.get("startTime");
            jobExp.put("startTime",st.substring(0,4)+"-"+st.substring(5,7)+"-01");
            String et = (String) jobExp.get("endTime");
            jobExp.put("endTime",et.substring(0,4)+"-"+et.substring(5,7)+"-01");
            //强转对象
            jobExperience = (JobExperience)
                    com.alibaba.fastjson.JSONObject.toJavaObject(jobExp,JobExperience.class);
            //时区处理
            jobExperience.getStartTime().setHours(jobExperience.getStartTime().getHours()+8);
            jobExperience.getEndTime().setHours(jobExperience.getEndTime().getHours()+8);
        }
        catch (Exception e) {
            return CommonResult.failure("日期转换失败");
        }

        //预处理传入参数
        if(jobExperience.getUnifiedId()==null){
            return CommonResult.failure("失败，unifiedid空");
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
    public CommonResult getJobExperience(@RequestParam("unifiedId") Integer unifiedId){
        //预处理传入参数
        if(unifiedId==null){
            return CommonResult.failure("失败，unifiedId空");
        }
        //开始查询数据
        List<JobExperience> jobExperience = jobExperienceService.getJobExperience(unifiedId);
        List<JSONObject> jsonObjects = new ArrayList<>();
        //时间处理和
        for (JobExperience job:jobExperience) {
            Calendar calender = Calendar.getInstance();
            calender.setTime(job.getStartTime());
            String st = calender.get(Calendar.YEAR)+"年"+(calender.get(Calendar.MONTH)>8?"":"0")+(calender.get(Calendar.MONTH)+1)+"月";
            calender.setTime(job.getEndTime());
            String et = calender.get(Calendar.YEAR)+"年"+(calender.get(Calendar.MONTH)>8?"":"0")+(calender.get(Calendar.MONTH)+1)+"月";

            JSONObject jsonObject = (JSONObject) (JSONObject.toJSON(job));
            jsonObject.put("pictureUrl",searchService.matchJobExperience(job.getEnterpriseName()));
            jsonObject.put("startTime",st);
            jsonObject.put("endTime",et);
            jsonObjects.add(jsonObject);
        }
        return CommonResult.success("查询工作经历成功",jsonObjects);
    }
}
