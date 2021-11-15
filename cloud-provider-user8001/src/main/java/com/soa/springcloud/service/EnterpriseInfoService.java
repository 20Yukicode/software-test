package com.soa.springcloud.service;

import com.soa.springcloud.entity.domain.EnterpriseInfo;
import com.soa.springcloud.entity.domain.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface EnterpriseInfoService {
    int create(Integer unifiedId);
    EnterpriseInfo getEnterpriseInfo(@Param("unified_id") int unified_id);
    int updateEnterpriseInfo(EnterpriseInfo enterpriseInfo);
}
