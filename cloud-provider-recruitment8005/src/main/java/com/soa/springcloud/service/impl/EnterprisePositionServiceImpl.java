package com.soa.springcloud.service.impl;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.Position;
import com.soa.springcloud.entities.*;
import com.soa.springcloud.mapper.PositionMapper;
import com.soa.springcloud.mapper.*;
import com.soa.springcloud.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class EnterprisePositionServiceImpl{
    @Resource
    private PositionMapper positionMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 判断是否企业用户
     * @param unifiedId id
     * @return bool
     */
    public Boolean isEnterprise(int unifiedId){
        User user=userMapper.selectById(unifiedId);//查找用户
        if(user!=null){
            if(user.getUserType()==0)return true;
            return false;
        }
        return false;
    }

    /**
     * 是否普通用户
     * @param unifiedId 用户id
     * @return bool
     */
    public Boolean isCommonUser(int unifiedId){
        User user=userMapper.selectById(unifiedId);//查找用户
        if(user!=null){
            if(user.getUserType()==1)return true;
            return false;
        }
        return false;
    }

    /**
     * 创建岗位
     * @param position 岗位
     * @return 是否成功
     */
    public int create(Position position) {
        position.setState(1);//设置岗位为激活状态
        return positionMapper.insert(position);//返回的是受到影响的行数
    }

    /**
     * 删除岗位
     * @param unifiedId 企业id
     * @param jobId 岗位id
     * @return 受影响行数
     */
    public int deletePosition(int unifiedId, int jobId) {
        Position position=positionMapper.selectById(jobId);
        if (position.getUnifiedId()!=unifiedId)return 0;
        position.setState(0);//惰性删除
        return positionMapper.updateById(position);//更新
    }

    /**
     * 更新岗位
     * @param position 岗位
     * @return
     */
    public int updatePosition(Position position) {
        return positionMapper.updateById(position);
    }

    /**
     * 获取岗位信息
     * @param unifiedId 企业id
     * @param jobId 岗位id
     * @return
     */
    public Position getPositionInfo(int unifiedId, int jobId) {
        Position position=positionMapper.selectById(jobId);
        if(position!=null){//能找到
            if(position.getState()==0)return null;
            if(position.getUnifiedId()==unifiedId) {//是这个企业创建的
                return position;
            }
            else return null;
        }
        return null;
    }

    /**
     * 获取企业的所有岗位
     * @param unifiedId
     * @return 岗位列表
     */
    //记得把未激活的岗位删除
    public List<Position> getPositionsById(int unifiedId) {
        QueryWrapper<Position> wrapper=new QueryWrapper<>();
        wrapper.eq("unified_id",unifiedId);
        wrapper.ne("state",0);//去除掉没激活的
        return positionMapper.selectList(wrapper);
    }

    //需要记录上一次浏览到的位置（下次开会再讨论）
    public List<Position> getRecommendedPositionsById(int unifiedId) {
        UserInfo userInfo=userInfoMapper.selectById(unifiedId);//找到用户信息
        List<String> prePosition=Arrays.asList(userInfo.getPrePosition().split(","));
        List<Position> positions=new ArrayList<>();
        prePosition.forEach(item->{
            QueryWrapper<Position> wrapper=new QueryWrapper<>();
            wrapper.eq("position_type",item);
            positions.addAll(positionMapper.selectList(wrapper));
        });
        positions.sort(Comparator.comparing(Position::getJobId));
        log.info("positions",positions);
        return positions;
    }

    

}
