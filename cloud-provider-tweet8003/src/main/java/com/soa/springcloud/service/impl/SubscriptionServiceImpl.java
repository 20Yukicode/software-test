package com.soa.springcloud.service.impl;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.Subscription;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.mapper.SubscriptionMapper;
import com.soa.springcloud.mapper.UserMapper;
import com.soa.springcloud.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService{
    @Resource
    private SubscriptionMapper subscriptionMapper;

    @Resource
    private UserMapper userMapper;
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
        Map<String,Object> map = new HashMap<>();
        map.put("unified_id",unifiedId);
        map.put("subscribe_id",subscribeId);
        if(subscriptionMapper.selectByMap(map).size()>0)return -1;
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

    @Override
    public JSONArray getRecommendList(Integer unifiedId){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        List userList = userMapper.selectList(userQueryWrapper);
        List subscriptionList = getSubscriptionList(unifiedId);

        int count = 0;

        JSONArray array = new JSONArray();

        for(int i=0;i<userList.size();i++){
            User user = (User)userList.get(i);
            if(getSubscription(unifiedId,user.getUnifiedId() )==0){
                array.add(user);
            }
        }

        if(array.size()<5)return array;

        else{
            JSONArray jsonArray = new JSONArray();
            Random random = new Random();

            while(count<5) {
                int index = random.nextInt(array.size());
                if (!jsonArray.contains(array.get(index))) {
                    count++;
                    jsonArray.add(array.get(index));
                }
            }
            return jsonArray;
        }

    }
}
