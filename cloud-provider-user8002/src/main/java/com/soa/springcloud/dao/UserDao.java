package com.soa.springcloud.dao;

import com.soa.springcloud.entities.EnterpriseInfo;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.entities.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: PaymentDao
 * @description: 支付持久层
 **/
@Mapper
public interface UserDao {
    public int create(User user);
    public User getUserById(@Param("unified_id") int unified_id);
    public User getUserByName(@Param("user_name") String user_name);
    public UserInfo getUserInfo(@Param("unified_id") int unified_id);
    public EnterpriseInfo getEnterpriseInfo(@Param("unified_id") int unified_id);
}