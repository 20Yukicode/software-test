package com.soa.springcloud.service;

import java.util.List;

public interface SubscriptionService {

    int putSubscription(Integer unifiedId,Integer subscribeId);
    int deleteSubscription(Integer unifiedId,Integer subscribeId);
    int getSubscription(Integer unifiedId,Integer subscribeId);
    List getSubscriptionList(Integer unifiedId);
    List getRecommendList (Integer unifiedId);
}
