package com.soa.springcloud.service;

import com.soa.springcloud.entities.User;
import java.util.List;

public interface SearchService {
    List<User> searchByTrueName(Integer unifiedId,String str);
}