package com.soa.springcloud.service;
import com.soa.springcloud.entities.Position;

public interface UserRecruitmentService {
    /**
     * 用户发送岗位申请
     * @param user_id 用户id
     * @param enterprise_id 企业id
     * @param job_id 岗位id
     * @param resume_id 简历id
     * @return 是否成功
     */
    int sendApplication(int user_id,int enterprise_id,int job_id,int resume_id);

    /**
     * 用户撤回申请岗位
     * @param user_id 用户id
     * @param enterprise_id 企业id
     * @param job_id 岗位id
     * @return 是否成功
     */
    int cancelApplication(int user_id,int enterprise_id,int job_id);

    /**
     * 查看投递过的所有岗位
     * @param user_id 用户id
     * @return 岗位数组
     */
    Position[] getAppliedPositions(int user_id);
}
