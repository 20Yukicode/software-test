package com.soa.springcloud.dto;

public class PositionInfoDto {
    private int unifiedId;
    private int jobId;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getUnifiedId() {
        return unifiedId;
    }

    public void setUnifiedId(int unifiedId) {
        this.unifiedId = unifiedId;
    }
}
