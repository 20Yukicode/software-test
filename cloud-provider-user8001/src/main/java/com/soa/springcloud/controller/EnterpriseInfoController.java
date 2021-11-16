package com.soa.springcloud.controller;

import com.soa.springcloud.entities.EnterpriseInfo;
import com.soa.springcloud.service.impl.EnterpriseInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class EnterpriseInfoController {
    @Resource
    private EnterpriseInfoServiceImpl enterpriseInfoService;

    /**
     * 获取企业信息
     * @param unified_id
     * @return
     */
    @GetMapping("/enterpriseinfo/get/{unified_id}")
    public EnterpriseInfo getEnterpriseInfo(@PathVariable("unified_id") int unified_id) {
        EnterpriseInfo enterpriseInfo = enterpriseInfoService.getEnterpriseInfo(unified_id);
        log.info("***查询结果：" + enterpriseInfo);
        return enterpriseInfo;
    }
    /**
     * 更改企业信息
     * @param enterpriseInfo
     * @return
     */
    @PostMapping(value = "/enterpriseinfo/info/update")
    public int updateEnterpriseInfo(@RequestBody EnterpriseInfo enterpriseInfo)
    {
        return enterpriseInfoService.updateEnterpriseInfo(enterpriseInfo);
    }
}
