package com.btv.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class JiFangBean {

    /**
     * resCount : 5
     * roomMetricInfoObjs : []
     * roomState : RED
     * roomName : room1
     * roomAlarmCount : 0
     * loginResult : true
     * normal : 0
     * abnormal : 1
     * roomJiguiOccCount : 1
     * roomLocation : testArea-北京-BTV-3F-room1
     * roomJiguiCount : 1
     * roomId : room_DFCEA489-C3F8-D2CC-BE53-1B0971FB3A60
     */

    private String resCount;
    private String roomState;
    private String roomName;
    private String roomAlarmCount;
    private String loginResult;
    private int normal;
    private int abnormal;
    private String roomJiguiOccCount;
    private String roomLocation;
    private String roomJiguiCount;
    private String roomId;
    private List<?> roomMetricInfoObjs;

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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomAlarmCount() {
        return roomAlarmCount;
    }

    public void setRoomAlarmCount(String roomAlarmCount) {
        this.roomAlarmCount = roomAlarmCount;
    }

    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public int getAbnormal() {
        return abnormal;
    }

    public void setAbnormal(int abnormal) {
        this.abnormal = abnormal;
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

    public List<?> getRoomMetricInfoObjs() {
        return roomMetricInfoObjs;
    }

    public void setRoomMetricInfoObjs(List<?> roomMetricInfoObjs) {
        this.roomMetricInfoObjs = roomMetricInfoObjs;
    }
}
