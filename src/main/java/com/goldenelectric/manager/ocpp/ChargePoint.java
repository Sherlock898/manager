package com.goldenelectric.manager.ocpp;

import java.util.HashMap;
import java.util.List;

import com.goldenelectric.manager.ocpp.Types.MeterValue;

// Class that stores the charging point information and status
public class ChargePoint {
    // Retrieved from uri path
    private String id;
    
    // Mnadatory Boot notification data
    private String chargePointModel;
    private String chargePointSerialNumber;
    private String chargePointVendor;
    private String firmwareVersion;
    private String chargePointUser;

    // Status of all the charging point connectors
    private HashMap<Integer, String> status;

    // Current transactions of the connectors or null if not in use}
    private HashMap<Long, TransactionInfo> transactions;

    // Actual meter values of the connectors
    private HashMap<String, MeterValue> meterValues;

    public ChargePoint(String id, String chargePointUser) {
        this.id = id;
        this.status = new HashMap<>();
        this.transactions = new HashMap<>();
        this.meterValues = new HashMap<>();
        this.chargePointUser = chargePointUser;
    }

    public String getChargePointUser(){
        return this.chargePointUser;
    }

    public void bootNotification(String chargePointModel, String chargePointSerialNumber, String chargePointVendor, String firmwareVersion) {
        this.chargePointModel = chargePointModel;
        this.chargePointSerialNumber = chargePointSerialNumber;
        this.chargePointVendor = chargePointVendor;
        this.firmwareVersion = firmwareVersion;
    }

    public void addMeterValues(String connectorId, List<MeterValue> meterValues) {
        for (MeterValue meterValue : meterValues) {
            this.meterValues.put(connectorId, meterValue);
        }
    }

    public String getChargePointId() {
        return id;
    }

    public void setChargePointId(String id) {
        this.id = id;
    }

    public HashMap<Integer, String> getConnectors() {
        return status;
    }

    public MeterValue getMeterValue(String connectorId) {
        return meterValues.get(connectorId);
    }

    public void updateStatus(Integer connectorId, String status) {
        this.status.put(connectorId, status);
    }

    public void addTransaction(Long transactionId, Integer connectorId, String status, String idTag, int meterStart, String timestamp) {
        TransactionInfo transaction = new TransactionInfo(transactionId, connectorId, timestamp, meterStart, status);
        transactions.put(transactionId, transaction);
    }

    public void stopTransaction(Long transactionId) {
        transactions.remove(transactionId);
    }


    public void addStatus(Integer connectorId, String status) {
        this.status.put(connectorId, status);
    }

    public void removeStatus(Integer connectorId) {
        this.status.remove(connectorId);
    }

    public TransactionInfo getTransaction(Long transactionId) {
        return transactions.get(transactionId);
    }

    public String getStatus(Integer connectorId) {
        return status.get(connectorId);
    }


    public boolean isConnected(Integer connectorId) {
        return status.containsKey(connectorId);
    }

    public boolean isFaulted(Integer connectorId) {
        return status.get(connectorId).equals("Faulted");
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public HashMap<Integer, String> getStatus() {
        return status;
    }

    public String getChargePointModel() {
        return chargePointModel;
    }

    public String getChargePointSerialNumber() {
        return chargePointSerialNumber;
    }

    public String getChargePointVendor() {
        return chargePointVendor;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setChargePointModel(String chargePointModel) {
        this.chargePointModel = chargePointModel;
    }

    public void setChargePointSerialNumber(String chargePointSerialNumber) {
        this.chargePointSerialNumber = chargePointSerialNumber;
    }

    public void setChargePointVendor(String chargePointVendor) {
        this.chargePointVendor = chargePointVendor;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }
    
    public void setStatus(HashMap<Integer, String> status) {
        this.status = status;
    }

    public boolean isCharging(Integer connectorId) {
        return status.get(connectorId).equals("Charging");
    }

    public Long getTransactionId(Integer connectorId) {
        for (Long transactionId : transactions.keySet()) {
            if (transactions.get(transactionId).getConnectorId() == connectorId) {
                return transactionId;
            }
        }
        return null;
    }
}

// 
class TransactionInfo{
    private Long id;
    private Integer connectorId;
    private String startTime;
    private String endTime;
    private int meterStart;
    private int meterStop;
    private String status;

    public TransactionInfo(Long id, Integer connectorId, String startTime, int meterStart, String status) {
        this.id = id;
        this.connectorId = connectorId;
        this.startTime = startTime;
        this.endTime = null;
        this.meterStart = meterStart;
        this.meterStop = -1;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Integer getConnectorId() {
        return connectorId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getmeterStop() {
        return meterStop;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setConnectorId(Integer connectorId) {
        this.connectorId = connectorId;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setmeterStop(int meterStop) {
        this.meterStop = meterStop;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMeterStart() {
        return meterStart;
    }

    public void setMeterStart(int meterStart) {
        this.meterStart = meterStart;
    }


}