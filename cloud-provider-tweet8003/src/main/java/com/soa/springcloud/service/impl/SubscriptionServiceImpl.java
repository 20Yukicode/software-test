package com.soa.springcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.Subscription;
import com.soa.springcloud.mapper.SubscriptionMapper;
import com.soa.springcloud.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

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
    public int getSubscription(Integer unifiedId,Integer subscribeId){

        List list = subscriptionMapper.selectByMap(new HashMap() {
            {
                put("unified_id", unifiedId);
                put("subscribe_id", subscribeId);
            }
        });
        return list.size();
    }

    @Override
    public int putSubscription(Integer unifiedId,Integer subscribeId){
        Subscription subscription = new Subscription();
        subscription.setSubscribeId(subscribeId);
        subscription.setUnifiedId(unifiedId);
        return subscriptionMapper.insert(subscription);

    }

    @Override
    public int deleteSubscription(Integer unifiedId,Integer subscribeId){
        QueryWrapper<Subscription> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unified_id",unifiedId).eq("subscribe_id",subscribeId);

        return subscriptionMapper.delete(queryWrapper);
    }

    @Override
    public List getSubscriptionList(Integer unifiedId){
        QueryWrapper<Subscription> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unified_id",unifiedId);
        return subscriptionMapper.selectList(queryWrapper);

    }
}
