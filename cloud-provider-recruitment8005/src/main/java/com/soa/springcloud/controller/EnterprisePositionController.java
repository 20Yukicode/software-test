package com.soa.springcloud.controller;
import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.Position;
import com.soa.springcloud.service.impl.UserRecruitmentServiceImpl;
import com.soa.springcloud.service.impl.EnterprisePositionServiceImpl;
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
public class EnterprisePositionController {
    @Resource
    private EnterprisePositionServiceImpl enterprisePositionService;
    @Resource
    private UserRecruitmentServiceImpl userRecruitmentService;

    @PostMapping(value = "/position/create")
    public CommonResult create(@RequestBody Position position,
                                        HttpServletRequest request){
        if(!enterprisePositionService.isEnterprise(position.getUnifiedId()))return CommonResult.failure("用户不是企业用户",null);
        if(enterprisePositionService.create(position)==1){
            //创建成功
            return CommonResult.success("创建成功",position);
        }
        return CommonResult.failure("插入失败",null);
    }

    @PostMapping(value = "/position/delete")
    public CommonResult delete(@RequestBody PositionDeleteDto dto,
                               HttpServletRequest request){
        if(enterprisePositionService.deletePosition(dto.getUnifiedId(),dto.getJobId())==1)return CommonResult.success("删除成功");
        else return CommonResult.failure("删除失败");
    }

    @PutMapping(value = "/position/update")
    public CommonResult update(@RequestBody Position position,
                               HttpServletRequest request){
        if(!enterprisePositionService.isEnterprise(position.getUnifiedId()))return CommonResult.failure("用户不是企业用户",null);
        if(enterprisePositionService.updatePosition(position)==1){
            //更新成功
            return CommonResult.success("更新成功",position);
        }
        return CommonResult.failure("更新失败",null);
    }

    @GetMapping(value = "/position/info")
    public CommonResult delete(@RequestParam int unifiedId,
                               @RequestParam int jobId,
                               HttpServletRequest request){
        Position position=enterprisePositionService.getPositionInfo(unifiedId,jobId);
        if(position!=null){
            return CommonResult.success("获取成功",position);
        }
        else return CommonResult.failure("获取失败",null);
    }

    @GetMapping(value = "/position/all")
    public CommonResult getAll(@RequestParam int unifiedId,
                               HttpServletRequest request){
        List<Position> position=enterprisePositionService.getPositionsById(unifiedId);
        if(position!=null){
            return CommonResult.success("获取成功",position);
        }
        else return CommonResult.failure("获取失败",null);
    }

}
