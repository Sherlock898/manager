package com.goldenelectric.manager.ocpp.payloads;

public class RemoteStartTransactionConf {
    private String status;

    public RemoteStartTransactionConf() {
    }

    public RemoteStartTransactionConf(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
