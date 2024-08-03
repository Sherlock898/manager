package com.goldenelectric.manager.ocpp.payloads;

import com.goldenelectric.manager.ocpp.Types.IdTagInfo;

public class StartTransactionConf {
    private IdTagInfo idTagInfo;
    private Integer transactionId;

    public StartTransactionConf() {}
    public StartTransactionConf(String status, Integer transactionId) {
        this.idTagInfo = new IdTagInfo(null, null, status);
        this.transactionId = transactionId;
    }
    public IdTagInfo getIdTagInfo() {return idTagInfo;}
    public void setIdTagInfo(IdTagInfo idTagInfo) {this.idTagInfo = idTagInfo;}
    public Integer getTransactionId() {return transactionId;}
    public void setTransactionId(Integer transactionId) {this.transactionId = transactionId;}
}


