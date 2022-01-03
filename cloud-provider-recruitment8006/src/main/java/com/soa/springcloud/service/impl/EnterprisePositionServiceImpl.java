package com.soa.springcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soa.springcloud.entities.Application;
import com.soa.springcloud.entities.Position;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.entities.UserInfo;
import com.soa.springcloud.mapper.ApplicationMapper;
import com.soa.springcloud.mapper.PositionMapper;
import com.soa.springcloud.mapper.UserInfoMapper;
import com.soa.springcloud.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class EnterprisePositionServiceImpl{
    @Resource
    private PositionMapper positionMapper;
    @Resource
    private ApplicationMapper applicationMapper;
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
            if("company".equals(user.getUserType()))return true;
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
            if("user".equals(user.getUserType()))return true;
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
        Date recordTime = new Date();
        recordTime.setHours(recordTime.getHours()+8);
        position.setRecordTime(recordTime);
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
     * 获取该企业创建的岗位信息
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
     * 获取指定岗位信息+用户是否申请该岗位
     * @param jobId 岗位id
     * @return
     */
    public JSONObject getSpecifiedPosition(int unifiedId, int jobId) {
        Position position=positionMapper.selectById(jobId);
        log.info("岗位："+position);
        if(position!=null){//能找到
            if(position.getState()==0)
                return null;
            else {
                QueryWrapper<Application> wrapper=new QueryWrapper<>();
                wrapper.eq("user_id",unifiedId);
                wrapper.eq("job_id",jobId);
                //applicationMapper.selectList(wrapper);
                //log.info("申请："+applicationMapper.selectList(wrapper));
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(position);
                jsonObject.put("ifApplied",applicationMapper.selectList(wrapper).size()<=0?false:true);
                return jsonObject;
            }
        }
        return null;
    }

    /**
     * 获取企业的所有岗位
     * @param unifiedId
     * @return 岗位列表
     */
    //记得把未激活的岗位删除
    public List<JSONObject> getPositionsById(int unifiedId,Integer momentId) {
        QueryWrapper<Position> wrapper=new QueryWrapper<>();
        wrapper.eq("unified_id",unifiedId);
        wrapper.ne("state",0);//去除掉没激活的
        List<JSONObject> positions=new ArrayList<>();
        positionMapper.selectList(wrapper).forEach(one->{
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(one);
            User user = userMapper.selectById(unifiedId);
            jsonObject.put("pictureUrl",user.getPictureUrl());
            jsonObject.put("userType",user.getUserType());
            jsonObject.put("briefInfo",user.getBriefInfo());
            jsonObject.put("trueName",user.getTrueName());
            positions.add(jsonObject);
            });

        //positions.sort(Comparator.comparing(Position::getJobId));
        positions.sort(new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                return o2.getInteger("jobId")-o1.getInteger("jobId");
            }
        });
        int size = positions.size();
        int cut = 0;
        if(momentId==null){
            log.info("momentId为空");
            //momentId=positions.get(0).getInteger("jobId")+1;
            //log.info("momentId="+momentId);
        }
        else{
            for(int i=0;i<size;i++){
                if(positions.get(i).getInteger("jobId")<momentId){
                    cut=i;
                    break;
                }
                if(i==size-1)cut=size;
            }
        }
        List<JSONObject> jsonObjects = new ArrayList<>();
        if(size>=cut+15) {
            jsonObjects.addAll(positions.subList(cut, cut + 15));
        } else jsonObjects.addAll(positions.subList(cut,size));
        return jsonObjects;
    }

    public List<JSONObject> getRecommendedPositionsById(int unifiedId,Integer momentId) {
        List<JSONObject> positions=new ArrayList<>();
        //若为公司用户
        if(userMapper.selectById(unifiedId).getUserType().equals("company")){
            positionMapper.selectList(null).forEach(one -> {
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(one);
                User user = userMapper.selectById(one.getUnifiedId());
                jsonObject.put("pictureUrl", user.getPictureUrl());
                jsonObject.put("userType", user.getUserType());
                jsonObject.put("briefInfo", user.getBriefInfo());
                jsonObject.put("trueName", user.getTrueName());
                positions.add(jsonObject);
            });
        }
        else{
            UserInfo userInfo=userInfoMapper.selectById(unifiedId);//找到用户信息
            List<String> prePosition = new ArrayList<>();
            if(!(userInfo.getPrePosition() == null || userInfo.getPrePosition().isEmpty())){
                prePosition=Arrays.asList(userInfo.getPrePosition().split(","));
            }

            log.info("positions",prePosition);
            //List<Position> positions=new ArrayList<>();


            prePosition.forEach(item->{
                if(userInfo.getPrePosition().equals("")){
                    positionMapper.selectList(null).forEach(one -> {
                        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(one);
                        User user = userMapper.selectById(one.getUnifiedId());
                        jsonObject.put("pictureUrl", user.getPictureUrl());
                        jsonObject.put("userType", user.getUserType());
                        jsonObject.put("briefInfo", user.getBriefInfo());
                        jsonObject.put("trueName", user.getTrueName());
                        positions.add(jsonObject);
                    });
                }
                else{
                    QueryWrapper<Position> wrapper = new QueryWrapper<>();
                    wrapper.eq("position_type", item);
                    wrapper.ne("state", 0);//去除掉没激活的
                    //positions.addAll(positionMapper.selectList(wrapper));
                    positionMapper.selectList(wrapper).forEach(one -> {
                        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(one);
                        User user = userMapper.selectById(one.getUnifiedId());
                        jsonObject.put("pictureUrl", user.getPictureUrl());
                        jsonObject.put("userType", user.getUserType());
                        jsonObject.put("briefInfo", user.getBriefInfo());
                        jsonObject.put("trueName", user.getTrueName());
                        positions.add(jsonObject);
                    });
                }
            });
        }
        //positions.sort(Comparator.comparing(Position::getJobId));
        positions.sort(new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                return o2.getInteger("jobId")-o1.getInteger("jobId");
            }
        });
        int size = positions.size();
        int cut = 0;
        if(momentId==null){
            log.info("momentId为空");
            //momentId=positions.get(0).getInteger("jobId")+1;
            //log.info("momentId="+momentId);
        }
        else{
            for(int i=0;i<size;i++){
                if(positions.get(i).getInteger("jobId")<momentId){
                    cut=i;
                    break;
                }
                if(i==size-1)cut=size;
            }
        }
        List<JSONObject> jsonObjects = new ArrayList<>();
        if(size>=cut+15) {
            jsonObjects.addAll(positions.subList(cut, cut + 15));
        } else jsonObjects.addAll(positions.subList(cut,size));
        return jsonObjects;
    }
}
