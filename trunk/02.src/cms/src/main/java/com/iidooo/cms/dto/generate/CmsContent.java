package com.iidooo.cms.dto.generate;

import com.iidooo.framework.dto.base.BaseDto;

public class CmsContent extends BaseDto{
    private Integer contentID;

    private Integer modelID;

    private Integer channelID;

    private Integer templateID;

    private String contentTitle;

    private String contentSubTitle;

    private String contentImageTitle;

    private String metaTitle;

    private String metaKeywords;

    private String metaDescription;

    private String contentSummary;

    private Integer sequence;

    private Integer isAllowComment;

    private String contentBody;

    public Integer getContentID() {
        return contentID;
    }

    public void setContentID(Integer contentID) {
        this.contentID = contentID;
    }

    public Integer getModelID() {
        return modelID;
    }

    public void setModelID(Integer modelID) {
        this.modelID = modelID;
    }

    public Integer getChannelID() {
        return channelID;
    }

    public void setChannelID(Integer channelID) {
        this.channelID = channelID;
    }

    public Integer getTemplateID() {
        return templateID;
    }

    public void setTemplateID(Integer templateID) {
        this.templateID = templateID;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle == null ? null : contentTitle.trim();
    }

    public String getContentSubTitle() {
        return contentSubTitle;
    }

    public void setContentSubTitle(String contentSubTitle) {
        this.contentSubTitle = contentSubTitle == null ? null : contentSubTitle.trim();
    }

    public String getContentImageTitle() {
        return contentImageTitle;
    }

    public void setContentImageTitle(String contentImageTitle) {
        this.contentImageTitle = contentImageTitle == null ? null : contentImageTitle.trim();
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle == null ? null : metaTitle.trim();
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords == null ? null : metaKeywords.trim();
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription == null ? null : metaDescription.trim();
    }

    public String getContentSummary() {
        return contentSummary;
    }

    public void setContentSummary(String contentSummary) {
        this.contentSummary = contentSummary == null ? null : contentSummary.trim();
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getIsAllowComment() {
        return isAllowComment;
    }

    public void setIsAllowComment(Integer isAllowComment) {
        this.isAllowComment = isAllowComment;
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody == null ? null : contentBody.trim();
    }
}