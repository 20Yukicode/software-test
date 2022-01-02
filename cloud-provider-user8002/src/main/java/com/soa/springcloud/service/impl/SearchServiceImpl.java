package com.soa.springcloud.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.User;
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

    /**
     * 匹配搜索字段与true_name
     *
     * @param str
     * @return
     */
    @Override
    public List<JSONObject> searchByTrueName(String str) {
        List<User> users = userMapper.selectByMap(null);
        List<JSONObject> resultJson = new ArrayList<>();
        Map<User, Double> userMap = new HashMap<>();
        List<User> result = new ArrayList<>();
        for (User one : users) {
            double cos = SearchUtils.similarScoreCos(one.getTrueName(), str);
            if (cos > 0.4) {
                JSONObject temp = JSONUtil.parseObj(one);
                temp.put("cos",cos);
                resultJson.add(temp);
            }
        }
        resultJson.sort(Comparator.comparing(obj->((JSONObject)obj).getDouble("cos")).reversed());
        return resultJson;
    }
    /**
     * 匹配工作经历中的enterprise_name和企业真实姓名true_name
     *
     * @param str
     * @return 企业picture_url
     */
    @Override
    public String matchJobExperience(String str) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(str)) {
            User user = userMapper.selectOne(queryWrapper.like("true_name", str).eq("user_type","company"));
            if(user!=null&&user.getPictureUrl()!=null)
                return user.getPictureUrl();
        }
        return "";
    }

    @Override
    public String matchEduExperience(String str) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(str)) {
            User user = userMapper.selectOne(queryWrapper.eq("true_name", str).eq("user_type","school"));
            if(user!=null&&user.getPictureUrl()!=null)
                return user.getPictureUrl();
        }
        return "";
    }
}
