package com.soa.springcloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.EduExperience;
import com.soa.springcloud.service.EduExperienceService;
import com.soa.springcloud.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class EduExperienceController {

    private final EduExperienceService eduExperienceService;
    private final SearchService searchService;

    public EduExperienceController(EduExperienceService eduExperienceService, SearchService searchService) {
        this.eduExperienceService = eduExperienceService;
        this.searchService = searchService;
    }

    /**
     * 增加教育经历
     * @param eduExp
     * @return
     */
    @PostMapping("/user/edu")
    public CommonResult postEduExperience(@RequestBody JSONObject eduExp) {
        EduExperience eduExperience;
        try{
            //获取字符串中的时间（“格式：yyyy年mm月”）
            String st = (String) eduExp.get("startTime");
            eduExp.put("startTime",st.substring(0,4)+"-"+st.substring(5,7)+"-01");
            String et = (String) eduExp.get("endTime");
            eduExp.put("endTime",et.substring(0,4)+"-"+et.substring(5,7)+"-01");
            //强转对象
            eduExperience = (EduExperience)
                    com.alibaba.fastjson.JSONObject.toJavaObject(eduExp,EduExperience.class);
            //时区处理
            eduExperience.getStartTime().setHours(eduExperience.getStartTime().getHours()+8);
            eduExperience.getEndTime().setHours(eduExperience.getEndTime().getHours()+8);
        }
        catch (Exception e) {
            return CommonResult.failure("日期转换失败");
        }
        //预处理传入参数
        if(eduExperience.getUnifiedId() ==null){
            return CommonResult.failure("失败，unifiedId空");
        }
        //开始插入数据
        if(eduExperienceService.postEduExperience(eduExperience)>0) {
            return CommonResult.success("增加教育经历成功",null);
        }
        return CommonResult.failure("增加失败");
    }

    /**
     * 删除教育经历
     * @param unifiedId
     * @param numId
     * @return
     */
    @DeleteMapping("/user/edu")
    public CommonResult deleteEduExperience(@RequestParam("unifiedId") Integer unifiedId,
                                            @RequestParam("numId") Integer numId) {
        if(unifiedId==null){
            return CommonResult.failure("失败，unifiedId空");
        }
        else if(numId==null){
            return CommonResult.failure("失败，numId空");
        }

        if(eduExperienceService.deleteEduExperience(unifiedId,numId)>0) {
            return CommonResult.success("删除教育经历成功",null);
        }
        return CommonResult.failure("删除失败");
    }

    /**
     * 更新教育经历
     * @param eduExp
     * @return
     */
    @PutMapping("/user/edu")
    public CommonResult putEduExperience(@RequestBody JSONObject eduExp) {
        EduExperience eduExperience;
        try{
            //获取字符串中的时间（“格式：yyyy年mm月”）
            String st = (String) eduExp.get("startTime");
            eduExp.put("startTime",st.substring(0,4)+"-"+st.substring(5,7)+"-01");
            String et = (String) eduExp.get("endTime");
            eduExp.put("endTime",et.substring(0,4)+"-"+et.substring(5,7)+"-01");
            //强转对象
            eduExperience = (EduExperience)
                    com.alibaba.fastjson.JSONObject.toJavaObject(eduExp,EduExperience.class);
            //时区处理
            eduExperience.getStartTime().setHours(eduExperience.getStartTime().getHours()+8);
            eduExperience.getEndTime().setHours(eduExperience.getEndTime().getHours()+8);
        }
        catch (Exception e) {
            return CommonResult.failure("日期转换失败");
        }
        //预处理传入参数
        if(eduExperience.getUnifiedId()==null){
            return CommonResult.failure("失败，unifiedId空");
        }
        //开始更新数据
        if(eduExperienceService.putEduExperience(eduExperience)>0) {
            return CommonResult.success("更新教育经历成功",null);
        }
        return CommonResult.failure("更新失败");
    }

    /**
     * 获取教育经历
     * @param unifiedId
     * @return
     */
    @GetMapping("/user/edu")
    public CommonResult getEduExperience(@RequestParam("unifiedId") Integer unifiedId){
        //预处理传入参数
        if(unifiedId==null){
            return CommonResult.failure("失败，unified_id空");
        }
        //开始查询数据
        List<EduExperience> eduExperience = eduExperienceService.getEduExperience(unifiedId);
        List<JSONObject> jsonObjects = new ArrayList<>();
        //时间处理
        for (EduExperience edu:eduExperience) {
            Calendar calender = Calendar.getInstance();
            calender.setTime(edu.getStartTime());
            String st = calender.get(Calendar.YEAR)+"年"+(calender.get(Calendar.MONTH)>8?"":"0")+(calender.get(Calendar.MONTH)+1)+"月";
            calender.setTime(edu.getEndTime());
            String et = calender.get(Calendar.YEAR)+"年"+(calender.get(Calendar.MONTH)>8?"":"0")+(calender.get(Calendar.MONTH)+1)+"月";

            JSONObject jsonObject = (JSONObject) (JSONObject.toJSON(edu));
            jsonObject.put("pictureUrl",searchService.matchEduExperience(edu.getCollegeName()));
            jsonObject.put("startTime",st);
            jsonObject.put("endTime",et);
            jsonObjects.add(jsonObject);
        }
        return CommonResult.success("查询教育经历成功",jsonObjects);
    }
}
