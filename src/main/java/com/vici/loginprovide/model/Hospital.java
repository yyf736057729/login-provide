package com.vici.loginprovide.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Hospital {
    private Integer hospitalId;

    private String hospitalName;

    private String hospitalUser;

    private String hospitalAddress;

    private String hospitalEmail;

    private String hospitalPhone;

    private Date hospitalCreateTime;

    private Date hospitalModifyTime;

    private Date hospitalExpirationTime;

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName == null ? null : hospitalName.trim();
    }

    public String getHospitalUser() {
        return hospitalUser;
    }

    public void setHospitalUser(String hospitalUser) {
        this.hospitalUser = hospitalUser == null ? null : hospitalUser.trim();
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress == null ? null : hospitalAddress.trim();
    }

    public String getHospitalEmail() {
        return hospitalEmail;
    }

    public void setHospitalEmail(String hospitalEmail) {
        this.hospitalEmail = hospitalEmail == null ? null : hospitalEmail.trim();
    }

    public String getHospitalPhone() {
        return hospitalPhone;
    }

    public void setHospitalPhone(String hospitalPhone) {
        this.hospitalPhone = hospitalPhone == null ? null : hospitalPhone.trim();
    }

    public Date getHospitalCreateTime() {
        return hospitalCreateTime;
    }

    public void setHospitalCreateTime(Date hospitalCreateTime) {
        this.hospitalCreateTime = hospitalCreateTime;
    }

    public Date getHospitalModifyTime() {
        return hospitalModifyTime;
    }

    public void setHospitalModifyTime(Date hospitalModifyTime) {
        this.hospitalModifyTime = hospitalModifyTime;
    }

    public Date getHospitalExpirationTime() {
        return hospitalExpirationTime;
    }

    public void setHospitalExpirationTime(Date hospitalExpirationTime) {
        this.hospitalExpirationTime = hospitalExpirationTime;
    }
}