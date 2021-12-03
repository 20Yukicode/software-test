package com.soa.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soa.springcloud.entities.Tweet;
import org.springframework.stereotype.Repository;

/**
* @author 86187
* @description 针对表【tweet】的数据库操作Mapper
* @createDate 2021-11-15 18:15:41
* @Entity entity.domain.Tweet
*/
@Repository
public interface TweetMapper extends BaseMapper<Tweet>{

}




