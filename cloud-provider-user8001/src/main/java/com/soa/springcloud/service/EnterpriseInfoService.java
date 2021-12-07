package com.soa.springcloud.service;

import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.EnterpriseInfo;
import org.apache.ibatis.annotations.Param;

public interface EnterpriseInfoService {
    int create(Integer unifiedId);
    EnterpriseInfo getEnterpriseInfo(int unifiedId);
    int updateEnterpriseInfo(EnterpriseInfo enterpriseInfo);
}
