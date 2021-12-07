package com.soa.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soa.springcloud.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

}




