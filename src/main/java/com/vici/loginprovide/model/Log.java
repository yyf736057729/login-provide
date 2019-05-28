package com.vici.loginprovide.model;

import java.util.Date;

public class Log {
    private Long logId;

    private Integer logUserId;

    private String logUser;

    private String logClassName;

    private String logRequestMethod;

    private String logContentType;

    private String logRequestType;

    private String logDescription;

    private String logAddress;

    private String logServerAddr;

    private String logRemoteAddr;

    private String logDeviceName;

    private String logBrowserName;

    private String logUserAgent;

    private String logRequestUri;

    private Date logTime;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Integer getLogUserId() {
        return logUserId;
    }

    public void setLogUserId(Integer logUserId) {
        this.logUserId = logUserId;
    }

    public String getLogUser() {
        return logUser;
    }

    public void setLogUser(String logUser) {
        this.logUser = logUser == null ? null : logUser.trim();
    }

    public String getLogClassName() {
        return logClassName;
    }

    public void setLogClassName(String logClassName) {
        this.logClassName = logClassName == null ? null : logClassName.trim();
    }

    public String getLogRequestMethod() {
        return logRequestMethod;
    }

    public void setLogRequestMethod(String logRequestMethod) {
        this.logRequestMethod = logRequestMethod == null ? null : logRequestMethod.trim();
    }

    public String getLogContentType() {
        return logContentType;
    }

    public void setLogContentType(String logContentType) {
        this.logContentType = logContentType == null ? null : logContentType.trim();
    }

    public String getLogRequestType() {
        return logRequestType;
    }

    public void setLogRequestType(String logRequestType) {
        this.logRequestType = logRequestType == null ? null : logRequestType.trim();
    }

    public String getLogDescription() {
        return logDescription;
    }

    public void setLogDescription(String logDescription) {
        this.logDescription = logDescription == null ? null : logDescription.trim();
    }

    public String getLogAddress() {
        return logAddress;
    }

    public void setLogAddress(String logAddress) {
        this.logAddress = logAddress == null ? null : logAddress.trim();
    }

    public String getLogServerAddr() {
        return logServerAddr;
    }

    public void setLogServerAddr(String logServerAddr) {
        this.logServerAddr = logServerAddr == null ? null : logServerAddr.trim();
    }

    public String getLogRemoteAddr() {
        return logRemoteAddr;
    }

    public void setLogRemoteAddr(String logRemoteAddr) {
        this.logRemoteAddr = logRemoteAddr == null ? null : logRemoteAddr.trim();
    }

    public String getLogDeviceName() {
        return logDeviceName;
    }

    public void setLogDeviceName(String logDeviceName) {
        this.logDeviceName = logDeviceName == null ? null : logDeviceName.trim();
    }

    public String getLogBrowserName() {
        return logBrowserName;
    }

    public void setLogBrowserName(String logBrowserName) {
        this.logBrowserName = logBrowserName == null ? null : logBrowserName.trim();
    }

    public String getLogUserAgent() {
        return logUserAgent;
    }

    public void setLogUserAgent(String logUserAgent) {
        this.logUserAgent = logUserAgent == null ? null : logUserAgent.trim();
    }

    public String getLogRequestUri() {
        return logRequestUri;
    }

    public void setLogRequestUri(String logRequestUri) {
        this.logRequestUri = logRequestUri == null ? null : logRequestUri.trim();
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }
}