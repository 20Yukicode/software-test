package com.soa.springcloud.service;

import com.soa.springcloud.entities.User;
import com.soa.springcloud.entities.Position;


public interface EnterprisePositionService {
    /**
     * 创建新岗位(由企业创建）
     * @param position 岗位
     * @return 是否成功
     */
    int create(Position position);

    /**
     * 删除特定岗位
     * @param unified_id 企业id
     * @param job_id 岗位id
     * @return 是否成功
     */
    int deletePosition(int unified_id, int job_id);

    /**
     * 更新岗位信息
     * @param position 岗位
     * @param job_id 岗位id
     * @return 是否成功
     */
    int updatePosition(Position position,int job_id);

    /**
     * 获取岗位详情
     * @param unified_id 用户id（判断用户类型？）
     * @param job_id 岗位id
     * @return 岗位
     */
    Position getPositionInfo(int unified_id,int job_id);

    /**
     * 查看某个企业所有岗位信息
     * @param unified_id 企业id
     * @return 岗位数组
     */
    Position[] getPositionsById(int unified_id);

    /**
     * 查看推荐岗位
     * @param unified_id 用户id
     * @return 岗位数组
     */
    Position[] getRecommendedPositionsById(int unified_id);

    /**
     * 企业查看某一岗位所有申请者信息及其简历
     * @param enterprise_id 企业id
     * @param job_id 岗位id
     * @return 用户数组
     */
    User[] getAllApplicants(int enterprise_id,int job_id);
}
