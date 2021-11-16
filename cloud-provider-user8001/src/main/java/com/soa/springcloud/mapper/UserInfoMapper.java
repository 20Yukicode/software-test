package com.soa.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soa.springcloud.entity.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 86187
* @description 针对表【user_info】的数据库操作Mapper
* @createDate 2021-11-15 18:15:41
* @Entity entity.domain.UserInfo
*/
@Mapper
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}




