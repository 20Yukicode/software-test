package com.soa.springcloud.service;

import com.soa.springcloud.entities.User;
import java.util.List;

public interface SearchService {
    List<User> searchByTrueName(String str);
    String matchJobExperience(String str);
    String matchEduExperience(String str);
}
