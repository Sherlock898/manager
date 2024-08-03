package com.goldenelectric.manager.ocpp.payloads;

public class RemoteStartTransactionReq {
    private Integer connectorId;
    private String idTag;
    // TODO: Define chargingProfile class
    private String chargingProfile;

    public RemoteStartTransactionReq() {
    }

    public RemoteStartTransactionReq(Integer connectorId, String idTag) {
        this.connectorId = connectorId;
        this.idTag = idTag;
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

    public String getChargingProfile() {
        return chargingProfile;
    }

    public void setChargingProfile(String chargingProfile) {
        this.chargingProfile = chargingProfile;
    }
    
}
