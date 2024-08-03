package com.goldenelectric.manager.ocpp.payloads;

public class RemoteStopTransactionReq {
    private int transactionId;

    public RemoteStopTransactionReq() {
    }

    public RemoteStopTransactionReq(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
}
