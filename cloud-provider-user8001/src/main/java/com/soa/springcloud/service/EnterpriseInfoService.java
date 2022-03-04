package com.soa.springcloud.service;

import com.soa.springcloud.entities.EnterpriseInfo;

public interface EnterpriseInfoService {
    int create(Integer unifiedId);
    EnterpriseInfo getEnterpriseInfo(int unifiedId);
    int insertOrUpdateEnterpriseInfo(EnterpriseInfo enterpriseInfo) throws IllegalAccessException;
}
