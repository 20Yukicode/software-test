package com.soa.springcloud.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.soa.springcloud.entities.EnterpriseInfo;
import com.soa.springcloud.entities.UserInfo;
import com.soa.springcloud.service.impl.EnterpriseInfoServiceImpl;
import com.soa.springcloud.service.impl.SubscriptionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class EnterpriseInfoController {
    @Resource
    private EnterpriseInfoServiceImpl enterpriseInfoService;
    @Resource
    private SubscriptionServiceImpl subscriptionService;
    /**
     * 获取企业信息
     * @param unified_id
     * @return
     */
    @GetMapping("/enterpriseinfo")
    public JSON getEnterpriseInfo(@RequestParam("uid") int unified_id,@RequestParam("sid") int subscribeId) {
        int subscribed = subscriptionService.isSubscribed(unified_id,subscribeId);
        if(unified_id==subscribeId)subscribed=2;
        EnterpriseInfo enterpriseInfo = enterpriseInfoService.getEnterpriseInfo(subscribeId);
        JSON json = JSONUtil.parse(enterpriseInfo);
        json.putByPath("is_subscribed",subscribed);
        log.info("***查询结果：" + json);
        return json;
    }
    /**
     * 更改企业信息
     * @param enterpriseInfo
     * @return
     */
    @PutMapping(value = "/enterpriseinfo")
    public int updateEnterpriseInfo(@RequestBody EnterpriseInfo enterpriseInfo)
    {
        return enterpriseInfoService.updateEnterpriseInfo(enterpriseInfo);
    }
}
