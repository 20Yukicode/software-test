package com.soa.springcloud.controller;

import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.service.HeadPictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
public class  HeadPictureController {
    private final HeadPictureService headPictureService;

    public HeadPictureController(HeadPictureService headPictureService) {
        this.headPictureService = headPictureService;
    }

    @PostMapping("/userinfo/pic")
    public CommonResult addHeadPicture(@RequestParam("unifiedId") Integer unifiedId,
                                       @RequestPart("file") MultipartFile file) throws IOException {
        //预处理传入参数
        if(unifiedId ==null){
            return CommonResult.failure("失败，unifiedId空");
        }
        //开始添加头像
        int i=headPictureService.addHeadPicture(unifiedId,file);
        if(i >0) {
            return CommonResult.success("添加头像成功",null);
        }
        return CommonResult.failure("添加头像失败");
    }
}
