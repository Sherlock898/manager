package com.goldenelectric.manager.ocpp.payloads;

import java.util.List;

import com.goldenelectric.manager.ocpp.Types.MeterValue;

public class MeterValueReq {
    private String connectorId;
    private String transactionId;
    private List<MeterValue> meterValue;

    public MeterValueReq() {
    }

    public String getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(String connectorId) {
        this.connectorId = connectorId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public List<MeterValue> getMeterValues() {
        return meterValue;
    }

    public void setMeterValues(List<MeterValue> meterValue) {
        this.meterValue = meterValue;
    }
    
}
