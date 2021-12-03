package com.soa.springcloud.controller;

import com.soa.springcloud.service.HeadPictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HeadPictureController {
    private final HeadPictureService headPictureService;

    public HeadPictureController(HeadPictureService headPictureService) {
        this.headPictureService = headPictureService;
    }
}
