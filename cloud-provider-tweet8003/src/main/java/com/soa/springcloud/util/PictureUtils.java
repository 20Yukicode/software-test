package com.soa.springcloud.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class PictureUtils {

    private static final String endpoint = "oss-cn-shanghai.aliyuncs.com";

    private static final String accessKeyId = "LTAI5tBG4652Uuc6Ljxi5hpu";

    private static final String accessKeySecret = "1SeLabxEsZdAPlRHN2kPkPzri3sxYi";

    private static final String bucketName = "soa-user-resume";

    private static final OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);

    public static void saveUrl(MultipartFile multipartFile,String path) {
        if (multipartFile != null) {
            try {
                InputStream inputstream = multipartFile.getInputStream();
                String name = multipartFile.getOriginalFilename();
                String fileName = path + "/" + name;
                ossClient.putObject(bucketName, fileName, inputstream);
            } catch (IOException ignored) {
            }
        }

    }
}
