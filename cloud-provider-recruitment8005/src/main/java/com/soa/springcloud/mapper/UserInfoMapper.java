package com.soa.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soa.springcloud.entities.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}




