package com.goldenelectric.manager.ocpp.payloads;

import java.util.List;

import com.goldenelectric.manager.ocpp.Types.MeterValue;

public class StopTransactionReq {
    private String idTag;
    private int meterStop;
    private String timestamp;
    private int transactionId;
    private String reason;
    private List<MeterValue> meterValues;

    public StopTransactionReq() {
    }

    public StopTransactionReq(String idTag, int meterStop, String timestamp, int transactionId, String reason, List<MeterValue> meterValues) {
        this.idTag = idTag;
        this.meterStop = meterStop;
        this.timestamp = timestamp;
        this.transactionId = transactionId;
        this.reason = reason;
        this.meterValues = meterValues;
    }

    public String getidTag() {
        return idTag;
    }

    public void setidTag(String idTag) {
        this.idTag = idTag;
    }

    public int getMeterStop() {
        return meterStop;
    }

    public void setMeterStop(int meterStop) {
        this.meterStop = meterStop;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<MeterValue> getMeterValues() {
        return meterValues;
    }

    public void setMeterValues(List<MeterValue> meterValues) {
        this.meterValues = meterValues;
    }
    
}
