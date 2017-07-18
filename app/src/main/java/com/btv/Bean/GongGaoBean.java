package com.btv.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/16.
 */

public class GongGaoBean implements Serializable{

    /**
     * DOCUMENT_TITLE : 公告发布的111
     * PUB_DEPT : 集团
     * EMPLOYEE_NAME : 曹宛莹
     * BO_INSTANCE_ID : 00-88ANFHF2-4YPB-8VKX-WX1T-498DHKZCZHAS
     * NOTICE_DETAIL_INFO : 公告发布的111
     * PUB_TIME : 2017-03-03 15:04
     */

    private String DOCUMENT_TITLE;
    private String PUB_DEPT;
    private String EMPLOYEE_NAME;
    private String BO_INSTANCE_ID;
    private String NOTICE_DETAIL_INFO;
    private String PUB_TIME;

    @Override
    public String toString() {
        return "GongGaoBean{" +
                "DOCUMENT_TITLE='" + DOCUMENT_TITLE + '\'' +
                ", PUB_DEPT='" + PUB_DEPT + '\'' +
                ", EMPLOYEE_NAME='" + EMPLOYEE_NAME + '\'' +
                ", BO_INSTANCE_ID='" + BO_INSTANCE_ID + '\'' +
                ", NOTICE_DETAIL_INFO='" + NOTICE_DETAIL_INFO + '\'' +
                ", PUB_TIME='" + PUB_TIME + '\'' +
                '}';
    }

    public String getDOCUMENT_TITLE() {
        return DOCUMENT_TITLE;
    }

    public void setDOCUMENT_TITLE(String DOCUMENT_TITLE) {
        this.DOCUMENT_TITLE = DOCUMENT_TITLE;
    }

    public String getPUB_DEPT() {
        return PUB_DEPT;
    }

    public void setPUB_DEPT(String PUB_DEPT) {
        this.PUB_DEPT = PUB_DEPT;
    }

    public String getEMPLOYEE_NAME() {
        return EMPLOYEE_NAME;
    }

    public void setEMPLOYEE_NAME(String EMPLOYEE_NAME) {
        this.EMPLOYEE_NAME = EMPLOYEE_NAME;
    }

    public String getBO_INSTANCE_ID() {
        return BO_INSTANCE_ID;
    }

    public void setBO_INSTANCE_ID(String BO_INSTANCE_ID) {
        this.BO_INSTANCE_ID = BO_INSTANCE_ID;
    }

    public String getNOTICE_DETAIL_INFO() {
        return NOTICE_DETAIL_INFO;
    }

    public void setNOTICE_DETAIL_INFO(String NOTICE_DETAIL_INFO) {
        this.NOTICE_DETAIL_INFO = NOTICE_DETAIL_INFO;
    }

    public String getPUB_TIME() {
        return PUB_TIME;
    }

    public void setPUB_TIME(String PUB_TIME) {
        this.PUB_TIME = PUB_TIME;
    }
}
