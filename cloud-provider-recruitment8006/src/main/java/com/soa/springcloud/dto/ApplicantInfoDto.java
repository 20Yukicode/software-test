package com.soa.springcloud.dto;

public class ApplicantInfoDto {
    private int unifiedId;
    private String brief_info;
    private String pictureUrl;
    private String resumeUrl;
    private String resumeName;

    public int getUnifiedId() {
        return unifiedId;
    }

    public void setUnifiedId(int unifiedId) {
        this.unifiedId = unifiedId;
    }

    public String getBrief_info() {
        return brief_info;
    }

    public void setBrief_info(String brief_info) {
        this.brief_info = brief_info;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }
}
