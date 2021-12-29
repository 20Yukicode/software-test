package com.soa.springcloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.soa.springcloud.dto.ApplicantInfoDto;
import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.Position;
import com.soa.springcloud.service.impl.UserRecruitmentServiceImpl;
import com.soa.springcloud.service.impl.EnterprisePositionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.soa.springcloud.dto.PositionDeleteDto;
import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class EnterprisePositionController {
    @Resource
    private EnterprisePositionServiceImpl enterprisePositionService;
    @Resource
    private UserRecruitmentServiceImpl userRecruitmentService;

    /**
     * 企业添加岗位信息
     * @param position
     * @return
     */
    @PostMapping(value = "/recruit/enterprise/position")
    public CommonResult create(@RequestBody Position position){
        if(!enterprisePositionService.isEnterprise(position.getUnifiedId()))
            return CommonResult.failure("用户不是企业用户",null);
        if(enterprisePositionService.create(position)==1){
            //创建成功
            return CommonResult.success("创建成功",position);
        }
        return CommonResult.failure("插入失败",null);
    }

    /**
     * 企业删除岗位信息
     * @param dto
     * @return
     */
    @DeleteMapping(value = "/recruit/enterprise/position")
    public CommonResult delete(@RequestBody PositionDeleteDto dto){
        if(enterprisePositionService.deletePosition(dto.getUnifiedId(),dto.getJobId())==1)
            return CommonResult.success("删除成功");
        else return CommonResult.failure("删除失败");
    }

    /**
     * 企业修改岗位信息
     * @param position
     * @return
     */
    @PutMapping(value = "/recruit/enterprise/position")
    public CommonResult update(@RequestBody Position position){
        if(!enterprisePositionService.isEnterprise(position.getUnifiedId()))
            return CommonResult.failure("用户不是企业用户",null);
        if(enterprisePositionService.updatePosition(position)==1){
            //更新成功
            return CommonResult.success("更新成功",position);
        }
        return CommonResult.failure("更新失败",null);
    }

    /**
     * 企业获取该岗位所有申请者
     * @param unifiedId
     * @param jobId
     * @return
     */
    @GetMapping(value = "/recruit/applicants")
    public CommonResult getAllApplicants(@RequestParam int unifiedId,
                                         @RequestParam int jobId){
        List<JSONObject> applicants= userRecruitmentService.getAllApplicants(unifiedId,jobId);
        if(applicants==null)
            return CommonResult.failure("该岗位还没有申请者",null);
        else
            return CommonResult.success("查找成功",applicants);
    }



}
