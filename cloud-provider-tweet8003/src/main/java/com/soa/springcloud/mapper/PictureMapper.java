package com.soa.springcloud.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soa.springcloud.entities.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 86187
* @description 针对表【picture】的数据库操作Mapper
* @createDate 2021-11-15 18:15:41
* @Entity entity.domain.Picture
*/
@Mapper
@Repository
public interface PictureMapper extends BaseMapper<Picture> {
    Integer maxNum(Integer tweetId);
}




