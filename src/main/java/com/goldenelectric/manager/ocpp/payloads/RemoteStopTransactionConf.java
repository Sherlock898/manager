package com.goldenelectric.manager.ocpp.payloads;

public class RemoteStopTransactionConf {
    private String status;

    public RemoteStopTransactionConf() {
    }

    public RemoteStopTransactionConf(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
