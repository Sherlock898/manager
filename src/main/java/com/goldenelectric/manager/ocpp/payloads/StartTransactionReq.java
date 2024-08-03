package com.goldenelectric.manager.ocpp.payloads;

public class StartTransactionReq {
    private Integer connectorId;
    private String idTag;
    private int meterStart;
    private String reservationId;
    private String timestamp;

    public StartTransactionReq() {
    }

    public StartTransactionReq(Integer connectorId, String idTag, int meterStart, String reservationId,
            String timestamp) {
        this.connectorId = connectorId;
        this.idTag = idTag;
        this.meterStart = meterStart;
        this.reservationId = reservationId;
        this.timestamp = timestamp;
    }

    public Integer getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(Integer connectorId) {
        this.connectorId = connectorId;
    }

    public String getIdTag() {
        return idTag;
    }

    public void setIdTag(String idTag) {
        this.idTag = idTag;
    }

    public int getMeterStart() {
        return meterStart;
    }

    public void setMeterStart(int meterStart) {
        this.meterStart = meterStart;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
