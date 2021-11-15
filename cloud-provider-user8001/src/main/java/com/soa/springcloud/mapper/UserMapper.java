package com.soa.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soa.springcloud.entities.EnterpriseInfo;
import com.soa.springcloud.entity.domain.User;
import com.soa.springcloud.entity.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.ManagedBean;

/**
* @author 86187
* @description 针对表【user】的数据库操作Mapper
* @createDate 2021-11-15 18:15:41
* @Entity entity.domain.User
*/
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
//    public int create(User user);
//    public User getUserById(@Param("unified_id") int unified_id);
//    public User getUserByName(@Param("user_name") String user_name);
//    public UserInfo getUserInfo(@Param("unified_id") int unified_id);
//    public EnterpriseInfo getEnterpriseInfo(@Param("unified_id") int unified_id);
//    public int updateUserInfo(UserInfo userInfo);
}




