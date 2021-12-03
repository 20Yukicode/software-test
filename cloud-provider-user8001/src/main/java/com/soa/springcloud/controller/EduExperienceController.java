package com.soa.springcloud.controller;

import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.EduExperience;
import com.soa.springcloud.service.EduExperienceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class EduExperienceController {

    private final EduExperienceService eduExperienceService;

    public EduExperienceController(EduExperienceService eduExperienceService) {
        this.eduExperienceService = eduExperienceService;
    }

    /**
     * 增加教育经历
     * @param eduExperience
     * @return
     */
    @PostMapping("/user/edu")
    public CommonResult postEduExperience(@RequestBody EduExperience eduExperience) {
        //预处理传入参数
        if(eduExperience.getUnifiedId() ==null){
            return CommonResult.failure("失败，uid空");
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
    public CommonResult deleteEduExperience(@RequestParam("uid") Integer unifiedId,
                                            @RequestParam("sid") Integer numId) {
        if(unifiedId==null){
            return CommonResult.failure("失败，uid空");
        }
        else if(numId==null){
            return CommonResult.failure("失败，sid空");
        }

        if(eduExperienceService.deleteEduExperience(unifiedId,numId)>0) {
            return CommonResult.success("删除教育经历成功",null);
        }
        return CommonResult.failure("删除失败");
    }

    /**
     * 更新教育经历
     * @param eduExperience
     * @return
     */
    @PutMapping("/user/edu")
    public CommonResult putEduExperience(@RequestBody EduExperience eduExperience){
        //预处理传入参数
        if(eduExperience.getUnifiedId()==null){
            return CommonResult.failure("失败，uid空");
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
    public CommonResult getEduExperience(@RequestParam("uid") Integer unifiedId){
        //预处理传入参数
        if(unifiedId==null){
            return CommonResult.failure("失败，uid空");
        }
        //开始查询数据
        return CommonResult.success("查询教育经历成功",eduExperienceService.getEduExperience(unifiedId));
    }
}
