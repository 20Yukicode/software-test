package com.soa.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soa.springcloud.entities.EnterpriseInfo;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86187
* @description 针对表【enterprise_info】的数据库操作Mapper
* @createDate 2021-11-15 18:15:40
* @Entity entity.domain.EnterpriseInfo
*/
@Mapper
public interface EnterpriseInfoMapper extends BaseMapper<EnterpriseInfo> {

}




