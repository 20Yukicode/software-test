package com.soa.springcloud.service;

import org.springframework.web.multipart.MultipartFile;

public interface HeadPictureService {
    int addHeadPicture(Integer unified_id, MultipartFile file);
}
