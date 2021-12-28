package com.soa.springcloud.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface HeadPictureService {
    int addHeadPicture(Integer unified_id, MultipartFile file) throws IOException;
}
