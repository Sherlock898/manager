package com.goldenelectric.manager.ocpp.payloads;

public class BootNotificationConf {
    private String currentTime;
    private int interval;
    private String status;

    public BootNotificationConf() {}
    public BootNotificationConf(String currentTime, int interval, String status) {
        this.currentTime = currentTime;
        this.interval = interval;
        this.status = status;
    }
    public String getCurrentTime() {return currentTime;}
    public void setCurrentTime(String currentTime) {this.currentTime = currentTime;}
    public int getInterval() {return interval;}
    public void setInterval(int interval) {this.interval = interval;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

}
