package com.soa.springcloud.controller;
import com.alibaba.fastjson.JSONObject;
import com.soa.springcloud.entities.Application;
import com.soa.springcloud.api.CommonResult;
import com.soa.springcloud.service.impl.UserRecruitmentServiceImpl;
import com.soa.springcloud.service.impl.EnterprisePositionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class UserRecruitmentController {
    @Resource
    private EnterprisePositionServiceImpl enterprisePositionService;
    @Resource
    private UserRecruitmentServiceImpl userRecruitmentService;

    /**
     * 用户申请岗位
     * @param application
     * @return
     */
    @PostMapping(value = "/recruit/application")
    public CommonResult apply(@RequestBody Application application){
        if(userRecruitmentService.sendApplication(application)==1)
            return CommonResult.success("发送成功",application);
        else
            return CommonResult.failure("发送失败",null);
    }

    /**
     * 用户删除岗位申请
     * @param application
     * @return
     */
    @DeleteMapping(value = "/recruit/application")
    public CommonResult delete(@RequestParam Integer userId,@RequestParam Integer jobId){
        if(userRecruitmentService.cancelApplication(userId,jobId)==1)
            return CommonResult.success("取消成功",null);
        else
            return CommonResult.failure("取消失败",null);
    }

    /**
     * 用户获取已投递的所有岗位
     * @param unifiedId
     * @return
     */
    @GetMapping(value = "/recruit/application")
    public CommonResult getAppliedPositions(@RequestParam int unifiedId){
        List<JSONObject> positions= userRecruitmentService.getAppliedPositions(unifiedId);
        return CommonResult.success("查找成功",positions);
    }

    /**
     * 用户查看特定岗位信息
     * @param unifiedId
     * @param jobId
     * @return
     */
    @GetMapping(value = "/recruit/position/specified")
    public CommonResult getPosition(@RequestParam int unifiedId, @RequestParam int jobId){
        JSONObject jsonObject =enterprisePositionService.getSpecifiedPosition(unifiedId,jobId);
        if(jsonObject!=null){
            return CommonResult.success("获取成功",jsonObject);
        }
        else return CommonResult.failure("获取失败",null);
    }

    /**
     * 用户获取企业所有岗位信息
     * @param unifiedId
     * @return
     */
    @GetMapping(value = "/recruit/position/all")
    public CommonResult getAll(@RequestParam int unifiedId,@RequestParam(required = false) Integer momentId){
        List<JSONObject> position=enterprisePositionService.getPositionsById(unifiedId,momentId);
        if(position!=null){
            return CommonResult.success("获取成功",position);
        }
        else return CommonResult.failure("获取失败",null);
    }

    /**
     * 用户查看推荐岗位
     * @param unifiedId
     * @return
     */
    @GetMapping(value = "/recruit/position/recommend")
    public CommonResult getRecommend(@RequestParam int unifiedId,@RequestParam(required = false) Integer momentId){
        List<JSONObject> position=enterprisePositionService.getRecommendedPositionsById(unifiedId,momentId);
        if(position!=null){
            return CommonResult.success("获取成功",position);
        }
        else return CommonResult.failure("获取失败",null);
    }
}
