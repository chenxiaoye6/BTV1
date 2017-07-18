package com.btv.Bean;

/**
 * Created by Administrator on 2017/1/12.
 */

public class FuWuBean {

    /**
     * bizsmState : GREEN
     * alarmNum : 0
     * bizsmId : 00000iuje2evo000
     * loginResult : true
     * bizsmName : 123
     * resNum : 0
     * isMonitor : true
     */

    private String bizsmState;
    private String alarmNum;
    private String bizsmId;
    private String loginResult;
    private String bizsmName;
    private String resNum;
    private String isMonitor;

    @Override
    public String toString() {
        return "FuWuBean{" +
                "bizsmState='" + bizsmState + '\'' +
                ", alarmNum='" + alarmNum + '\'' +
                ", bizsmId='" + bizsmId + '\'' +
                ", loginResult='" + loginResult + '\'' +
                ", bizsmName='" + bizsmName + '\'' +
                ", resNum='" + resNum + '\'' +
                ", isMonitor='" + isMonitor + '\'' +
                '}';
    }

    public String getBizsmState() {
        return bizsmState;
    }

    public void setBizsmState(String bizsmState) {
        this.bizsmState = bizsmState;
    }

    public String getAlarmNum() {
        return alarmNum;
    }

    public void setAlarmNum(String alarmNum) {
        this.alarmNum = alarmNum;
    }

    public String getBizsmId() {
        return bizsmId;
    }

    public void setBizsmId(String bizsmId) {
        this.bizsmId = bizsmId;
    }

    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }

    public String getBizsmName() {
        return bizsmName;
    }

    public void setBizsmName(String bizsmName) {
        this.bizsmName = bizsmName;
    }

    public String getResNum() {
        return resNum;
    }

    public void setResNum(String resNum) {
        this.resNum = resNum;
    }

    public String getIsMonitor() {
        return isMonitor;
    }

    public void setIsMonitor(String isMonitor) {
        this.isMonitor = isMonitor;
    }
}
