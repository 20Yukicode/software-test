package com.soa.springcloud.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RefreshScope
@Component
public class FileUtils {

    private static final String endpoint = "oss-cn-shanghai.aliyuncs.com";

    private static final String accessKeyId = "LTAI5tBG4652Uuc6Ljxi5hpu";

    private static final String accessKeySecret = "1SeLabxEsZdAPlRHN2kPkPzri3sxYi";

    private static final String bucketName = "soa-user-resume";
//    @Value("${file.endpoint}")
//    public void setEndpoint(String endpoint) {
//        FileUtils.endpoint = endpoint;
//}
//
//    @Value("${file.accessKeyId}")
//    public void setAccessKeyId(String accessKeyId) {
//        FileUtils.accessKeyId = accessKeyId;
//    }
//
//    @Value("${file.accessKeySecret}")
//    public void setAccessKeySecret(String accessKeySecret) {
//        FileUtils.accessKeySecret = accessKeySecret;
//    }
//
//    @Value("${file.bucketName}")
//    public void setBucketName(String bucketName) {
//        FileUtils.bucketName = bucketName;
//    }


    public static void saveUrl(MultipartFile multipartFile, String path) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        if (multipartFile != null) {
            try {
                InputStream inputstream = multipartFile.getInputStream();
                String name = multipartFile.getOriginalFilename();
                String fileName = path + "/" + name;
                ossClient.putObject(bucketName, fileName, inputstream);
                //return "https:/ / " + bucketName + "." + endpoint + "/" + fileName;
            } catch (IOException ignored) {
            }
        }
    }
}
