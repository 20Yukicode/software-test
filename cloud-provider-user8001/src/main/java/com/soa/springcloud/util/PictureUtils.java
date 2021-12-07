package com.soa.springcloud.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
public class PictureUtils {
    /**
     * ip地址
     */
    private static final String ip = "47.102.217.130";
    /**
     * 开放端口号
     */
    private static final String host = "666";
    public static String saveUrl(MultipartFile multipartFile,String path) throws IOException {
        File file = new File(path);
        //创建文件夹
        file.mkdirs();
        //存储文件
        String _path=path+multipartFile.getOriginalFilename();
        multipartFile.transferTo(new File(_path));

        String[] pics = path.split("C:/upload/");
        return ip+":"+host+"/"+pics[1]+multipartFile.getOriginalFilename();
    }
}
