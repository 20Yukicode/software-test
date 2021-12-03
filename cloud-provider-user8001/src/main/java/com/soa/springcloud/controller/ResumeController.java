package com.soa.springcloud.controller;

import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.Resume;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ResumeController {
    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("/user/resume")
    public CommonResult getResume(@RequestParam("uid") Integer unified_id) {
        //预处理传入参数
        if(unified_id ==null){
            return CommonResult.failure("失败，uid空");
        }
        //开始查询简历
        List<Resume> resumes = resumeService.getResume(unified_id);
        if(resumes !=null) {
            return CommonResult.success("查询简历成功",resumes);
        }
        return CommonResult.failure("查询简历失败");
    }


    @PostMapping("/user/resume")
    public CommonResult addResume(@RequestParam("uid") Integer unified_id) {
        //预处理传入参数
        if(unified_id ==null){
            return CommonResult.failure("失败，uid空");
        }
        //开始添加简历
        int i = resumeService.addResume();
        if(i >0) {
            return CommonResult.success("添加简历成功",null);
        }
        return CommonResult.failure("添加简历失败");
    }


    @DeleteMapping("/user/resume")
    public CommonResult deleteResume(@RequestParam("uid") Integer unified_id) {
        //预处理传入参数
        if(unified_id ==null){
            return CommonResult.failure("失败，uid空");
        }
        //开始添加简历
        int i = resumeService.deleteResume();
        if(i >0) {
            return CommonResult.success("删除简历成功",null);
        }
        return CommonResult.failure("删除简历失败");
    }

}
