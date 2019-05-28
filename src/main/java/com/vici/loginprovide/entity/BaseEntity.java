package com.vici.loginprovide.entity;

import java.io.Serializable;

/**
 * @ClassName BaseEntity
 * @Description 请求返回的实体基类
 * @Author vici_yyb
 * @Date 2019/1/21 9:31
 * @Version V1.0
 **/
public class BaseEntity implements Serializable {
    
    private String returnCode;
    private String retLog;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getRetLog() {
        return retLog;
    }

    public void setRetLog(String retLog) {
        this.retLog = retLog;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "returnCode='" + returnCode + '\'' +
                ", retLog='" + retLog + '\'' +
                '}';
    }
}
