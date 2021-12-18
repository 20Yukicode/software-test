
package com.soa.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RefreshScope
@Slf4j
public class ConfigClientController
{
    @Value("${file.localPath}")
    private String localPath;

    @Value("${file.webPath}")
    private String webPath;

    @GetMapping("/configInfo")
    public String getConfigInfo()
    {
        log.info("配置："+webPath);
        return webPath;
    }
}



