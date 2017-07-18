package com.btv.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

public class SJifangBean {


    private List<RoomListBean> RoomList;

    public List<RoomListBean> getRoomList() {
        return RoomList;
    }

    public void setRoomList(List<RoomListBean> RoomList) {
        this.RoomList = RoomList;
    }

    public static class RoomListBean {
        /**
         * resCount : 6
         * roomMetricInfoObjs : [{"roomMetricName":"烟感(smoke2)","roomMetricValue":"60","roomMetricType":"analog_smoke","roomMetricStatus":"0"},{"roomMetricName":"机柜(111)","roomMetricValue":"60","roomMetricType":"analog","roomMetricStatus":"1"},{"roomMetricName":"湿度1(ho1)","roomMetricValue":"80","roomMetricType":"analog_humidity","roomMetricStatus":"1"},{"roomMetricName":"湿度(no2)","roomMetricValue":"45","roomMetricType":"analog_humidity","roomMetricStatus":"0"},{"roomMetricName":"温度(temp123)","roomMetricValue":"30","roomMetricType":"analog_temperature","roomMetricStatus":"1"},{"roomMetricName":"湿度(test)","roomMetricValue":"25","roomMetricType":"analog_humidity","roomMetricStatus":"1"}]
         * roomState : RED
         * loginResult : true
         * roomAlarmCount : 1
         * roomName : room1
         * abnormal : 1
         * normal : 0
         * roomJiguiOccCount : 1
         * roomLocation : testArea-北京-BTV-3F-room1
         * roomJiguiCount : 1
         * roomId : room_DFCEA489-C3F8-D2CC-BE53-1B0971FB3A60
         */

        private String resCount;
        private String roomState;
        private String loginResult;
        private String roomAlarmCount;
        private String roomName;
        private int abnormal;
        private int normal;
        private String roomJiguiOccCount;
        private String roomLocation;
        private String roomJiguiCount;
        private String roomId;
        private List<RoomMetricInfoObjsBean> roomMetricInfoObjs;

        public String getResCount() {
            return resCount;
        }

        public void setResCount(String resCount) {
            this.resCount = resCount;
        }

        public String getRoomState() {
            return roomState;
        }

        public void setRoomState(String roomState) {
            this.roomState = roomState;
        }

        public String getLoginResult() {
            return loginResult;
        }

        public void setLoginResult(String loginResult) {
            this.loginResult = loginResult;
        }

        public String getRoomAlarmCount() {
            return roomAlarmCount;
        }

        public void setRoomAlarmCount(String roomAlarmCount) {
            this.roomAlarmCount = roomAlarmCount;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public int getAbnormal() {
            return abnormal;
        }

        public void setAbnormal(int abnormal) {
            this.abnormal = abnormal;
        }

        public int getNormal() {
            return normal;
        }

        public void setNormal(int normal) {
            this.normal = normal;
        }

        public String getRoomJiguiOccCount() {
            return roomJiguiOccCount;
        }

        public void setRoomJiguiOccCount(String roomJiguiOccCount) {
            this.roomJiguiOccCount = roomJiguiOccCount;
        }

        public String getRoomLocation() {
            return roomLocation;
        }

        public void setRoomLocation(String roomLocation) {
            this.roomLocation = roomLocation;
        }

        public String getRoomJiguiCount() {
            return roomJiguiCount;
        }

        public void setRoomJiguiCount(String roomJiguiCount) {
            this.roomJiguiCount = roomJiguiCount;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public List<RoomMetricInfoObjsBean> getRoomMetricInfoObjs() {
            return roomMetricInfoObjs;
        }

        public void setRoomMetricInfoObjs(List<RoomMetricInfoObjsBean> roomMetricInfoObjs) {
            this.roomMetricInfoObjs = roomMetricInfoObjs;
        }

        public static class RoomMetricInfoObjsBean {
            /**
             * roomMetricName : 烟感(smoke2)
             * roomMetricValue : 60
             * roomMetricType : analog_smoke
             * roomMetricStatus : 0
             */

            private String roomMetricName;
            private String roomMetricValue;
            private String roomMetricType;
            private String roomMetricStatus;

            public String getRoomMetricName() {
                return roomMetricName;
            }

            public void setRoomMetricName(String roomMetricName) {
                this.roomMetricName = roomMetricName;
            }

            public String getRoomMetricValue() {
                return roomMetricValue;
            }

            public void setRoomMetricValue(String roomMetricValue) {
                this.roomMetricValue = roomMetricValue;
            }

            public String getRoomMetricType() {
                return roomMetricType;
            }

            public void setRoomMetricType(String roomMetricType) {
                this.roomMetricType = roomMetricType;
            }

            public String getRoomMetricStatus() {
                return roomMetricStatus;
            }

            public void setRoomMetricStatus(String roomMetricStatus) {
                this.roomMetricStatus = roomMetricStatus;
            }
        }
    }
}
