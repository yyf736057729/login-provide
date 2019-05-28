package com.vici.loginprovide.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Integer userId;

    private String userName;

    private String userPhone;

    private String userPwd;

    private String userStatus;

    private String userHospitalName;

    private Integer userHospitalId;

    private Date userCreateTime;

    private Date userModifyTime;

    private Hospital hospital;

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    @JsonIgnore
    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus == null ? null : userStatus.trim();
    }

    public String getUserHospitalName() {
        return userHospitalName;
    }

    public void setUserHospitalName(String userHospitalName) {
        this.userHospitalName = userHospitalName == null ? null : userHospitalName.trim();
    }

    public Integer getUserHospitalId() {
        return userHospitalId;
    }

    public void setUserHospitalId(Integer userHospitalId) {
        this.userHospitalId = userHospitalId;
    }

    public Date getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(Date userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public Date getUserModifyTime() {
        return userModifyTime;
    }

    public void setUserModifyTime(Date userModifyTime) {
        this.userModifyTime = userModifyTime;
    }
}