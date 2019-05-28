package com.vici.loginprovide.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Child {
    private Integer childId;

    private String childName;

    private String childSex;

    private String childAge;

    private String childGuardian;

    private String childPhone;

    @JsonIgnore
    private String childPwd;

    private Integer childLock;

    private Integer childTrain;

    private Integer childCount;

    private Integer childProgram;

    private Integer childGold;

    private Integer childHospitalId;

    private Date childCreateTime;

    private Date childModifyTime;

    private Date childTrainTime;

    private Date childCutTime;


    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName == null ? null : childName.trim();
    }

    public String getChildSex() {
        return childSex;
    }

    public void setChildSex(String childSex) {
        this.childSex = childSex == null ? null : childSex.trim();
    }

    public String getChildAge() {
        return childAge;
    }

    public void setChildAge(String childAge) {
        this.childAge = childAge == null ? null : childAge.trim();
    }

    public String getChildGuardian() {
        return childGuardian;
    }

    public void setChildGuardian(String childGuardian) {
        this.childGuardian = childGuardian == null ? null : childGuardian.trim();
    }

    public String getChildPhone() {
        return childPhone;
    }

    public void setChildPhone(String childPhone) {
        this.childPhone = childPhone == null ? null : childPhone.trim();
    }

    public String getChildPwd() {
        return childPwd;
    }

    public void setChildPwd(String childPwd) {
        this.childPwd = childPwd == null ? null : childPwd.trim();
    }

    public Integer getChildLock() {
        return childLock;
    }

    public void setChildLock(Integer childLock) {
        this.childLock = childLock;
    }

    public Integer getChildTrain() {
        return childTrain;
    }

    public void setChildTrain(Integer childTrain) {
        this.childTrain = childTrain;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public Integer getChildProgram() {
        return childProgram;
    }

    public void setChildProgram(Integer childProgram) {
        this.childProgram = childProgram;
    }

    public Integer getChildGold() {
        return childGold;
    }

    public void setChildGold(Integer childGold) {
        this.childGold = childGold;
    }

    public Integer getChildHospitalId() {
        return childHospitalId;
    }

    public void setChildHospitalId(Integer childHospitalId) {
        this.childHospitalId = childHospitalId;
    }

    public Date getChildCreateTime() {
        return childCreateTime;
    }

    public void setChildCreateTime(Date childCreateTime) {
        this.childCreateTime = childCreateTime;
    }

    public Date getChildModifyTime() {
        return childModifyTime;
    }

    public void setChildModifyTime(Date childModifyTime) {
        this.childModifyTime = childModifyTime;
    }

    public Date getChildTrainTime() {
        return childTrainTime;
    }

    public void setChildTrainTime(Date childTrainTime) {
        this.childTrainTime = childTrainTime;
    }

    public Date getChildCutTime() {
        return childCutTime;
    }

    public void setChildCutTime(Date childCutTime) {
        this.childCutTime = childCutTime;
    }

    @Override
    public String toString() {
        return "Child{" +
                "childId=" + childId +
                ", childName='" + childName + '\'' +
                ", childSex='" + childSex + '\'' +
                ", childAge='" + childAge + '\'' +
                ", childGuardian='" + childGuardian + '\'' +
                ", childPhone='" + childPhone + '\'' +
                ", childPwd='" + childPwd + '\'' +
                ", childLock=" + childLock +
                ", childTrain=" + childTrain +
                ", childCount=" + childCount +
                ", childProgram=" + childProgram +
                ", childGold=" + childGold +
                ", childHospitalId=" + childHospitalId +
                ", childCreateTime=" + childCreateTime +
                ", childModifyTime=" + childModifyTime +
                ", childTrainTime=" + childTrainTime +
                ", childCutTime=" + childCutTime +
                '}';
    }
}