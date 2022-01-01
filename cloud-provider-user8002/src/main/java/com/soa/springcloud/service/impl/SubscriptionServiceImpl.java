package com.soa.springcloud.service.impl;

import com.soa.springcloud.entities.Subscription;
import com.soa.springcloud.mapper.SubscriptionMapper;
import com.soa.springcloud.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService{
    @Resource
    private SubscriptionMapper subscriptionMapper;
    /**
     * 检查订阅关系
     * @param unifiedId——查看方
     * @param subscribeId——被查看方
     * @return
     */
    @Override
    public int isSubscribed(Integer unifiedId,Integer subscribeId){

        List list = subscriptionMapper.selectByMap(new HashMap() {
            {
                put("unified_id", unifiedId);
                put("subscribe_id", subscribeId);
            }
        });
        return list.size();
    }
    /**
     * 关注数量
     * @param unifiedId
     * @return
     */
    public int followNum(Integer unifiedId){
        Map<String,Object> map = new HashMap<>();
        map.put("unified_id",unifiedId);
        List<Subscription> subscriptions = subscriptionMapper.selectByMap(map);
        log.info("关注数量："+subscriptions.size());
        return subscriptions.size();
    }
    /**
     * 粉丝数量
     * @param unifiedId
     * @return
     */
    public int fansNum(Integer unifiedId){
        Map<String,Object> map = new HashMap<>();
        map.put("subscribe_id",unifiedId);
        List<Subscription> subscriptions = subscriptionMapper.selectByMap(map);
        log.info("粉丝数量："+subscriptions.size());
        return subscriptions.size();
    }
}
