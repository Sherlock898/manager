package com.goldenelectric.manager.ocpp.payloads;

public class HeartbeatConf {
    private String currentTime;
    
    public HeartbeatConf(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
