package com.daoli.sheng.tai.entity;

import java.util.Date;

public class ShengTaiExamEntity {
    private Integer id;

    private String examId;

    private Integer parentExamId;

    private String examType;

    private String examName;

    private String examDesc;

    private Integer examScore;

    private String examStatus;

    private String createUid;

    private Date startTime;

    private Date endTime;

    private Date modifyTime;

    private Date createTime;

    private Byte valid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public Integer getParentExamId() {
        return parentExamId;
    }

    public void setParentExamId(Integer parentExamId) {
        this.parentExamId = parentExamId;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamDesc() {
        return examDesc;
    }

    public void setExamDesc(String examDesc) {
        this.examDesc = examDesc;
    }

    public Integer getExamScore() {
        return examScore;
    }

    public void setExamScore(Integer examScore) {
        this.examScore = examScore;
    }

    public String getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(String examStatus) {
        this.examStatus = examStatus;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }
}