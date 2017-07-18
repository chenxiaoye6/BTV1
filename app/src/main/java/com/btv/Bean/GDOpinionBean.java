package com.btv.Bean;

/**
 * Created by Administrator on 2017/4/27.
 */

public class GDOpinionBean {

    /**
     * conclusion :
     * userName : xuzsh
     * inputDate : Thu Apr 27 15:59:25 CST 2017
     * opinion : sadfasdf
     */

    private String conclusion;
    private String userName;
    private String inputDate;
    private String opinion;

    @Override
    public String toString() {
        return "GDOpinionBean{" +
                "conclusion='" + conclusion + '\'' +
                ", userName='" + userName + '\'' +
                ", inputDate='" + inputDate + '\'' +
                ", opinion='" + opinion + '\'' +
                '}';
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}
