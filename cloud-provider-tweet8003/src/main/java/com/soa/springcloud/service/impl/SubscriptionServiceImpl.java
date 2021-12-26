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
import java.util.*;

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
        if(subscriptionMapper.selectByMap(map).size()>0)
            return -1;
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
    public List<User> getRecommendList(Integer unifiedId){
        /*QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        List userList = userMapper.selectList(userQueryWrapper);
        List subscriptionList = getSubscriptionList(unifiedId);

        int count = 0;

        JSONArray array = new JSONArray();

        for(int i=0;i<userList.size();i++){
            User user = (User)userList.get(i);
            if(getSubscription(unifiedId,user.getUnifiedId() )==0&&!unifiedId.equals(user.getUnifiedId())){
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
        }*/
        User user = userMapper.selectById(unifiedId);
        List<User> users = userMapper.selectByMap(null);
        users.remove(user);
        return recommendValue(user,users);
    }

    public List<User> recommendValue(User user,List<User> users){
        Map<User,Double> userMap = new HashMap<>();
        List<User> result = new ArrayList<>();
        int maxSubNum = 1;
        for(User one : users){
            maxSubNum = Math.max(one.getSubscribeNum(),maxSubNum);
        }
        for(User one : users){
            double cos = similarScoreCos(one.getTrueName(), user.getBriefInfo());
            double subValue = one.getSubscribeNum()/maxSubNum;
            userMap.put(one,cos+subValue);
        }
        List<Map.Entry<User, Double>> list = new ArrayList<Map.Entry<User, Double>>(userMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<User, Double>>() {
            public int compare(Map.Entry<User, Double> o1,
                               Map.Entry<User, Double> o2) {
                return (int)Math.floor(o2.getValue()-o1.getValue());
            }
        });
        int count = 0;
        for(Map.Entry<User, Double> one : list){
            if(count>=5)break;
            count++;
            //log.info(one.getKey().getTrueName()+" 相似度："+one.getValue());
            result.add(one.getKey());
        }
        return result;
    }

    public static double similarScoreCos(String text1, String text2){
        if(text1 == null || text2 == null){
            //只要有一个文本为null，规定相似度分值为0，表示完全不相等
            return 0.0;
        }else if("".equals(text1)&&"".equals(text2)) return 1.0;
        Set<Integer> ASII=new TreeSet<>();
        Map<Integer, Integer> text1Map=new HashMap<>();
        Map<Integer, Integer> text2Map=new HashMap<>();
        for(int i=0;i<text1.length();i++){
            Integer temp1=new Integer(text1.charAt(i));
            if(text1Map.get(temp1)==null) text1Map.put(temp1,1);
            else text1Map.put(temp1,text1Map.get(temp1)+1);
            ASII.add(temp1);
        }
        for(int j=0;j<text2.length();j++){
            Integer temp2=new Integer(text2.charAt(j));
            if(text2Map.get(temp2)==null) text2Map.put(temp2,1);
            else text2Map.put(temp2,text2Map.get(temp2)+1);
            ASII.add(temp2);
        }
        double xy=0.0;
        double x=0.0;
        double y=0.0;
        //计算
        for (Integer it : ASII) {
            Integer t1=text1Map.get(it)==null?0:text1Map.get(it);
            Integer t2=text2Map.get(it)==null?0:text2Map.get(it);
            xy+=t1*t2;
            x+=Math.pow(t1, 2);
            y+=Math.pow(t2, 2);
        }
        if(x==0.0||y==0.0) return 0.0;
        return xy/Math.sqrt(x*y);
    }
    public int addSubscriptionNum(Integer unifiedId){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unified_id",unifiedId);
        User user = userMapper.selectOne(queryWrapper);
        user.setSubscribeNum(user.getSubscribeNum()+1);
        return userMapper.update(user,queryWrapper);

    }
    public int subtractSubscriptionNum(Integer unifiedId){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unified_id",unifiedId);
        User user = userMapper.selectOne(queryWrapper);

        user.setSubscribeNum(user.getSubscribeNum()-1);
        return userMapper.update(user,queryWrapper);

    }
}
