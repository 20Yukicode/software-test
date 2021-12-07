package com.soa.springcloud.controller;

import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.service.HeadPictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class HeadPictureController {
    private final HeadPictureService headPictureService;

    public HeadPictureController(HeadPictureService headPictureService) {
        this.headPictureService = headPictureService;
    }

    @PostMapping("/userinfo/pic")
    public CommonResult addHeadPicture(@RequestParam("unifiedId") Integer unified_id,
                                       @RequestPart("file") MultipartFile file) {
        //预处理传入参数
        if(unified_id ==null){
            return CommonResult.failure("失败，unifiedId空");
        }
        //开始添加头像
        int i=0;
        if(i >0) {
            return CommonResult.success("添加简历成功",null);
        }
        return CommonResult.failure("添加简历失败");
    }
}
