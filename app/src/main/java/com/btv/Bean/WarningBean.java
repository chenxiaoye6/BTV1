package com.btv.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/29.
 */

public class WarningBean implements Serializable{

    /**
     * level : 严重
     * mobile : 13212345678
     * levelColor : 0xFF9933
     * metricValue :
     * isConfirm : false
     * objectTypeName : Windows服务器
     * notificationId : 38DACD6D-3DC1-8C31-714D-EEE1CBDDB347
     * metricName : 硬盘读写速率
     * confirmUser :
     * loginResult : true
     * location : room2_111
     * resourceTypeId : -Resource-device-host-server-WindowsServer-
     * instanceId : 105
     * objectName : WIN034(硬盘:HD0)
     * occurDate : 2016-12-28 09:36:00
     * platform : MochaBSM
     * levelNum : 4
     * serviceInfoStr : {relaservice:'00000iqt0ekgu000__TestService__RED,00000itax5zgk000__ttttt__YELLOW'}
     * content : 硬盘读写速率违反红色阈值(指标当前值:509573Bps>红色阈值80Bps)
     * ipAddress : 192.168.40.149
     * confirmDate :
     * serviceInfoList : []
     * isLink : false
     * isAttention : true
     * moduleId : model
     * domain : 集团
     * email : admin@xxx.com.cn
     * metricUnit : Bps
     */
    private String level;
    private String mobile;
    private String levelColor;
    private String metricValue;
    private String isConfirm;
    private String objectTypeName;
    private String notificationId;
    private String metricName;
    private String confirmUser;
    private String loginResult;
    private String location;
    private String resourceTypeId;
    private String instanceId;
    private String objectName;
    private String occurDate;
    private String platform;
    private String levelNum;
    private String serviceInfoStr;
    private String content;
    private String ipAddress;
    private String confirmDate;
    private String isLink;
    private String isAttention;
    private String moduleId;
    private String domain;
    private String email;
    private String metricUnit;
    private List<?> serviceInfoList;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLevelColor() {
        return levelColor;
    }

    public void setLevelColor(String levelColor) {
        this.levelColor = levelColor;
    }

    public String getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(String metricValue) {
        this.metricValue = metricValue;
    }

    public String getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(String isConfirm) {
        this.isConfirm = isConfirm;
    }

    public String getObjectTypeName() {
        return objectTypeName;
    }

    public void setObjectTypeName(String objectTypeName) {
        this.objectTypeName = objectTypeName;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser;
    }

    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(String resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(String occurDate) {
        this.occurDate = occurDate;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(String levelNum) {
        this.levelNum = levelNum;
    }

    public String getServiceInfoStr() {
        return serviceInfoStr;
    }

    public void setServiceInfoStr(String serviceInfoStr) {
        this.serviceInfoStr = serviceInfoStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getIsLink() {
        return isLink;
    }

    public void setIsLink(String isLink) {
        this.isLink = isLink;
    }

    public String getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(String isAttention) {
        this.isAttention = isAttention;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMetricUnit() {
        return metricUnit;
    }

    public void setMetricUnit(String metricUnit) {
        this.metricUnit = metricUnit;
    }

    public List<?> getServiceInfoList() {
        return serviceInfoList;
    }

    public void setServiceInfoList(List<?> serviceInfoList) {
        this.serviceInfoList = serviceInfoList;
    }

    @Override
    public String toString() {
        return "WarningobjBean{" +
                "level='" + level + '\'' +
                ", mobile='" + mobile + '\'' +
                ", levelColor='" + levelColor + '\'' +
                ", metricValue='" + metricValue + '\'' +
                ", isConfirm='" + isConfirm + '\'' +
                ", objectTypeName='" + objectTypeName + '\'' +
                ", notificationId='" + notificationId + '\'' +
                ", metricName='" + metricName + '\'' +
                ", confirmUser='" + confirmUser + '\'' +
                ", loginResult='" + loginResult + '\'' +
                ", location='" + location + '\'' +
                ", resourceTypeId='" + resourceTypeId + '\'' +
                ", instanceId='" + instanceId + '\'' +
                ", objectName='" + objectName + '\'' +
                ", occurDate='" + occurDate + '\'' +
                ", platform='" + platform + '\'' +
                ", levelNum='" + levelNum + '\'' +
                ", serviceInfoStr='" + serviceInfoStr + '\'' +
                ", content='" + content + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", confirmDate='" + confirmDate + '\'' +
                ", isLink='" + isLink + '\'' +
                ", isAttention='" + isAttention + '\'' +
                ", moduleId='" + moduleId + '\'' +
                ", domain='" + domain + '\'' +
                ", email='" + email + '\'' +
                ", metricUnit='" + metricUnit + '\'' +
                ", serviceInfoList=" + serviceInfoList +
                '}';
    }
}
