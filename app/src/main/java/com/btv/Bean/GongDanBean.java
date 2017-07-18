package com.btv.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/11.
 */

public class GongDanBean implements Serializable{

    /**
     * taskId : 1345
     * accountId : 10-UPX7PR15-WQ4Y-R5L6-H6WH-6YLLL9JB92CW
     * workFormState : inprocess
     * boInstId : 00-7NA37OCH-V4GC-XKRK-Y6KG-GV1VZ6647NQZ
     * docTitle : 测试新的意见标签
     * workFormArrivalTime : 2016/11/30 15:21:34
     * requesetCategory : 办公设备故障
     */

    private String taskId;
    private String accountId;
    private String workFormState;
    private String boInstId;
    private String docTitle;
    private String workFormArrivalTime;
    private String requesetCategory;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getWorkFormState() {
        return workFormState;
    }

    public void setWorkFormState(String workFormState) {
        this.workFormState = workFormState;
    }

    public String getBoInstId() {
        return boInstId;
    }

    public void setBoInstId(String boInstId) {
        this.boInstId = boInstId;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getWorkFormArrivalTime() {
        return workFormArrivalTime;
    }

    public void setWorkFormArrivalTime(String workFormArrivalTime) {
        this.workFormArrivalTime = workFormArrivalTime;
    }

    public String getRequesetCategory() {
        return requesetCategory;
    }

    public void setRequesetCategory(String requesetCategory) {
        this.requesetCategory = requesetCategory;
    }
}
