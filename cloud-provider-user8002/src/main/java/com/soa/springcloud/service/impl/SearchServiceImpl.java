package com.soa.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.mapper.SubscriptionMapper;
import com.soa.springcloud.mapper.UserMapper;
import com.soa.springcloud.service.SearchService;
import com.soa.springcloud.util.SearchUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private SubscriptionMapper subscriptionMapper;

    /**
     * 匹配搜索字段与true_name
     * @param str
     * @return
     */
    @Override
    public List<User> searchByTrueName(String str) {
        List<User> users = userMapper.selectByMap(null);
        Map<User,Double> userMap = new HashMap<>();
        List<User> result = new ArrayList<>();
        for(User one : users){
            double cos = SearchUtils.similarScoreCos(one.getTrueName(), str);
            if(cos>0.2){
                userMap.put(one,cos);
            }
        }
        List<Map.Entry<User, Double>> list = new ArrayList<Map.Entry<User, Double>>(userMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<User, Double>>() {
            public int compare(Map.Entry<User, Double> o1,
                               Map.Entry<User, Double> o2) {
                return (int)Math.floor(o2.getValue()-o1.getValue());
            }
        });
        for(Map.Entry<User, Double> one : list){
            log.info(one.getKey().getTrueName()+" 相似度："+one.getValue());
            result.add(one.getKey());
        }
        /*for(User one : users){
            double cos = SearchUtils.similarScoreCos(one.getTrueName(), str);
            if(cos>0){
                result.add(one);
            }
        }*/
        return result;
    }
}
