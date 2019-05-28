package com.vici.loginprovide.model;

import java.util.Date;

public class Program {
    private Integer programId;

    private String programName;

    private Integer programNumber;

    private Date programTime;

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName == null ? null : programName.trim();
    }

    public Integer getProgramNumber() {
        return programNumber;
    }

    public void setProgramNumber(Integer programNumber) {
        this.programNumber = programNumber;
    }

    public Date getProgramTime() {
        return programTime;
    }

    public void setProgramTime(Date programTime) {
        this.programTime = programTime;
    }
}