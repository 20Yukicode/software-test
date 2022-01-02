package com.soa.springcloud.service;

import cn.hutool.json.JSONObject;
import com.soa.springcloud.entities.User;

import java.util.List;

public interface SearchService {
    List<JSONObject> searchByTrueName(String str);
    String matchJobExperience(String str);
    String matchEduExperience(String str);
}
