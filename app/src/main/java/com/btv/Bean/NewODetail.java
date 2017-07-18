package com.btv.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/9.
 */

public class NewODetail {


    /**
     * boInstanceId :
     * title : 测试新的意见标签
     * duID : 10-Z65WSKTI-4O8R-9BPJ-2FIW-983ZDE8OX59K
     * slaTime : -
     * duOrgName : 部门A
     * businessSysName : 通用类
     * empNo : A3
     * businessSysNumber : 003000
     * process : [{"userName":"曹宛莹","nextActivity":"请求池","activityName":"创建请求","receiveTime":"2016-11-30 15:12:41","sendTime":"2016-11-30 15:13:58"}]
     * duOrgID : 60-N0YJB6OF-92NV-0X49-D69Y-JJ5ZWOYG51I9
     * createTime : 2016-11-30 15:12:41
     * duEmail : z917q3@ss.ss
     * duMobile :
     * priority : 1
     * reqCategoryName : 办公设备故障
     * desc :
     * duName : A3
     * number : REQ00000184
     * reqCategoryNumber : 000003
     * duWorkplace :
     * workformState : inprocess
     * slaName : -
     * requestMethod : false
     * isLeader : 2
     * opinion : []
     * remark :
     * duTitle : 10-Z65WSKTI-4O8R-9BPJ-2FIW-983ZDE8OX59K|A3|40-KILNTXJP-YG8I-2XRE-X94A-X4ST28TDVD70#10-Z65WSKTI-4O8R-9BPJ-2FIW-983ZDE8OX59K#60-N0YJB6OF-92NV-0X49-D69Y-JJ5ZWOYG51I9#00-00000000-0000-0000-0000-000000000006#-1#60-N0YJB6OF-92NV-0X49-D69Y-JJ5ZWOYG51I9
     * faultTime : 2016-11-30 15:13:28
     * createUsername : 10-UPX7PR15-WQ4Y-R5L6-H6WH-6YLLL9JB92CW
     * reqCategoryID : 00-NIR536VT-UYDU-CI3M-DW2V-PPTA13CP1PIV
     * businessSysID : 00-UOCS0NKQ-60IQ-0WZ1-B4RV-L8919AOUPZXK
     */

    private String boInstanceId;
    private String title;
    private String duID;
    private String slaTime;
    private String duOrgName;
    private String businessSysName;
    private String empNo;
    private String businessSysNumber;
    private String duOrgID;
    private String createTime;
    private String duEmail;
    private String duMobile;
    private String priority;
    private String reqCategoryName;
    private String desc;
    private String duName;
    private String number;
    private String reqCategoryNumber;
    private String duWorkplace;
    private String workformState;
    private String slaName;
    private String requestMethod;
    private String isLeader;
    private String remark;
    private String duTitle;
    private String faultTime;
    private String createUsername;
    private String reqCategoryID;
    private String businessSysID;
    private ArrayList<ProcessBean> process;
    private List<?> opinion;

    public String getBoInstanceId() {
        return boInstanceId;
    }

    public void setBoInstanceId(String boInstanceId) {
        this.boInstanceId = boInstanceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuID() {
        return duID;
    }

    public void setDuID(String duID) {
        this.duID = duID;
    }

    public String getSlaTime() {
        return slaTime;
    }

    public void setSlaTime(String slaTime) {
        this.slaTime = slaTime;
    }

    public String getDuOrgName() {
        return duOrgName;
    }

    public void setDuOrgName(String duOrgName) {
        this.duOrgName = duOrgName;
    }

    public String getBusinessSysName() {
        return businessSysName;
    }

    public void setBusinessSysName(String businessSysName) {
        this.businessSysName = businessSysName;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getBusinessSysNumber() {
        return businessSysNumber;
    }

    public void setBusinessSysNumber(String businessSysNumber) {
        this.businessSysNumber = businessSysNumber;
    }

    public String getDuOrgID() {
        return duOrgID;
    }

    public void setDuOrgID(String duOrgID) {
        this.duOrgID = duOrgID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDuEmail() {
        return duEmail;
    }

    public void setDuEmail(String duEmail) {
        this.duEmail = duEmail;
    }

    public String getDuMobile() {
        return duMobile;
    }

    public void setDuMobile(String duMobile) {
        this.duMobile = duMobile;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getReqCategoryName() {
        return reqCategoryName;
    }

    public void setReqCategoryName(String reqCategoryName) {
        this.reqCategoryName = reqCategoryName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDuName() {
        return duName;
    }

    public void setDuName(String duName) {
        this.duName = duName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReqCategoryNumber() {
        return reqCategoryNumber;
    }

    public void setReqCategoryNumber(String reqCategoryNumber) {
        this.reqCategoryNumber = reqCategoryNumber;
    }

    public String getDuWorkplace() {
        return duWorkplace;
    }

    public void setDuWorkplace(String duWorkplace) {
        this.duWorkplace = duWorkplace;
    }

    public String getWorkformState() {
        return workformState;
    }

    public void setWorkformState(String workformState) {
        this.workformState = workformState;
    }

    public String getSlaName() {
        return slaName;
    }

    public void setSlaName(String slaName) {
        this.slaName = slaName;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(String isLeader) {
        this.isLeader = isLeader;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDuTitle() {
        return duTitle;
    }

    public void setDuTitle(String duTitle) {
        this.duTitle = duTitle;
    }

    public String getFaultTime() {
        return faultTime;
    }

    public void setFaultTime(String faultTime) {
        this.faultTime = faultTime;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public String getReqCategoryID() {
        return reqCategoryID;
    }

    public void setReqCategoryID(String reqCategoryID) {
        this.reqCategoryID = reqCategoryID;
    }

    public String getBusinessSysID() {
        return businessSysID;
    }

    public void setBusinessSysID(String businessSysID) {
        this.businessSysID = businessSysID;
    }

    public ArrayList<ProcessBean> getProcess() {
        return process;
    }

    public void setProcess(ArrayList<ProcessBean> process) {
        this.process = process;
    }

    public List<?> getOpinion() {
        return opinion;
    }

    public void setOpinion(List<?> opinion) {
        this.opinion = opinion;
    }

    public static class ProcessBean implements Serializable {
        /**
         * userName : 曹宛莹
         * nextActivity : 请求池
         * activityName : 创建请求
         * receiveTime : 2016-11-30 15:12:41
         * sendTime : 2016-11-30 15:13:58
         */

        private String userName;
        private String nextActivity;
        private String activityName;
        private String receiveTime;
        private String sendTime;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getNextActivity() {
            return nextActivity;
        }

        public void setNextActivity(String nextActivity) {
            this.nextActivity = nextActivity;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getReceiveTime() {
            return receiveTime;
        }

        public void setReceiveTime(String receiveTime) {
            this.receiveTime = receiveTime;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        @Override
        public String toString() {
            return "ProcessBean{" +
                    "userName='" + userName + '\'' +
                    ", nextActivity='" + nextActivity + '\'' +
                    ", activityName='" + activityName + '\'' +
                    ", receiveTime='" + receiveTime + '\'' +
                    ", sendTime='" + sendTime + '\'' +
                    '}';
        }
    }
}
