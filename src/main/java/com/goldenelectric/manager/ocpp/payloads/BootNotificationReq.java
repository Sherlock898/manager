package com.goldenelectric.manager.ocpp.payloads;

public class BootNotificationReq {
    private String chargeBoxSerialNumber;
    private String chargePointModel;
    private String chargePointSerialNumber;
    private String chargePointVendor;
    private String firmwareVersion;
    private String iccid;
    private String imsi;
    private String meterSerialNumber;
    private String meterType;

    public BootNotificationReq() {
    }

    public BootNotificationReq(String chargeBoxSerialNumber, String chargePointModel, String chargePointSerialNumber,
            String chargePointVendor, String firmwareVersion, String iccid, String imsi, String meterSerialNumber,
            String meterType) {
        this.chargeBoxSerialNumber = chargeBoxSerialNumber;
        this.chargePointModel = chargePointModel;
        this.chargePointSerialNumber = chargePointSerialNumber;
        this.chargePointVendor = chargePointVendor;
        this.firmwareVersion = firmwareVersion;
        this.iccid = iccid;
        this.imsi = imsi;
        this.meterSerialNumber = meterSerialNumber;
        this.meterType = meterType;
    }

    public String getChargeBoxSerialNumber() {
        return chargeBoxSerialNumber;
    }

    public void setChargeBoxSerialNumber(String chargeBoxSerialNumber) {
        this.chargeBoxSerialNumber = chargeBoxSerialNumber;
    }

    public String getChargePointModel() {
        return chargePointModel;
    }

    public void setChargePointModel(String chargePointModel) {
        this.chargePointModel = chargePointModel;
    }

    public String getChargePointSerialNumber() {
        return chargePointSerialNumber;
    }

    public void setChargePointSerialNumber(String chargePointSerialNumber) {
        this.chargePointSerialNumber = chargePointSerialNumber;
    }

    public String getChargePointVendor() {
        return chargePointVendor;
    }

    public void setChargePointVendor(String chargePointVendor) {
        this.chargePointVendor = chargePointVendor;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getMeterSerialNumber() {
        return meterSerialNumber;
    }

    public void setMeterSerialNumber(String meterSerialNumber) {
        this.meterSerialNumber = meterSerialNumber;
    }

    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }
}
