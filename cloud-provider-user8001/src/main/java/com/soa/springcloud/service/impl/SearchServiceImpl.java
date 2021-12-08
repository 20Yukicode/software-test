package com.soa.springcloud.service.impl;

import com.soa.springcloud.entities.User;
import com.soa.springcloud.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
    /**
     * 匹配搜索字段与true_name
     * @param unifiedId
     * @param str
     * @return
     */
    @Override
    public List<User> searchByTrueName(Integer unifiedId, String str) {

        return null;
    }
}