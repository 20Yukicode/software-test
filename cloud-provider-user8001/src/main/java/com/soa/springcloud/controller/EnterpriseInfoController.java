package com.soa.springcloud.controller;

import com.soa.springcloud.entity.domain.EnterpriseInfo;
import com.soa.springcloud.service.impl.EnterpriseInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class EnterpriseInfoController {
    @Resource
    private EnterpriseInfoServiceImpl enterpriseInfoService;

    @GetMapping("/enterpriseinfo/get/{unified_id}")
    public EnterpriseInfo getEnterpriseInfo(@PathVariable("unified_id") int unified_id) {
        EnterpriseInfo enterpriseInfo = enterpriseInfoService.getEnterpriseInfo(unified_id);
        log.info("***查询结果：" + enterpriseInfo);
        return enterpriseInfo;
    }
    /**
     * 更改用户信息
     * @param enterpriseInfo
     * @return
     */
    @PostMapping(value = "/enterpriseinfo/info/update")
    public int updateEnterpriseInfo(@RequestBody EnterpriseInfo enterpriseInfo)
    {
        return enterpriseInfoService.updateEnterpriseInfo(enterpriseInfo);
    }
}
