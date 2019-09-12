package com.daoli.sheng.tai.entity;

import java.util.Date;

public class ShengtaiExamRecordAdditionEntity {
    private Integer id;

    private Integer examRecordPid;

    private String additionName;

    private String additionLocation;

    private String createUid;

    private Date modifyTime;

    private Date createTime;

    private Byte valid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExamRecordPid() {
        return examRecordPid;
    }

    public void setExamRecordPid(Integer examRecordPid) {
        this.examRecordPid = examRecordPid;
    }

    public String getAdditionName() {
        return additionName;
    }

    public void setAdditionName(String additionName) {
        this.additionName = additionName;
    }

    public String getAdditionLocation() {
        return additionLocation;
    }

    public void setAdditionLocation(String additionLocation) {
        this.additionLocation = additionLocation;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
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