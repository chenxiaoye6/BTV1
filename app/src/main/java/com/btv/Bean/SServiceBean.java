package com.btv.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

//监控中的资源文件
public class SServiceBean {


    private List<BizSvcListBean> BizSvcList;

    public List<BizSvcListBean> getBizSvcList() {
        return BizSvcList;
    }

    public void setBizSvcList(List<BizSvcListBean> BizSvcList) {
        this.BizSvcList = BizSvcList;
    }

    @Override
    public String toString() {
        return "SServiceBean{" +
                "BizSvcList=" + BizSvcList +
                '}';
    }

    public static class BizSvcListBean {
        /**
         * bizsmState :
         * alarmNum :
         * bizsmId :
         * bizsmName :
         * loginResult :
         * resNum :
         * isMonitor :
         */

        private String bizsmState;
        private String alarmNum;
        private String bizsmId;
        private String bizsmName;
        private String loginResult;
        private String resNum;
        private String isMonitor;

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

        public String getBizsmName() {
            return bizsmName;
        }

        public void setBizsmName(String bizsmName) {
            this.bizsmName = bizsmName;
        }

        public String getLoginResult() {
            return loginResult;
        }

        public void setLoginResult(String loginResult) {
            this.loginResult = loginResult;
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

        @Override
        public String toString() {
            return "BizSvcListBean{" +
                    "bizsmState='" + bizsmState + '\'' +
                    ", alarmNum='" + alarmNum + '\'' +
                    ", bizsmId='" + bizsmId + '\'' +
                    ", bizsmName='" + bizsmName + '\'' +
                    ", loginResult='" + loginResult + '\'' +
                    ", resNum='" + resNum + '\'' +
                    ", isMonitor='" + isMonitor + '\'' +
                    '}';
        }
    }
}
