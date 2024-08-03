package com.goldenelectric.manager.ocpp.payloads;

import com.goldenelectric.manager.ocpp.Types.IdTagInfo;

public class StopTransactionConf {
    private IdTagInfo idTagInfo;

    public StopTransactionConf() {
    }

    public StopTransactionConf(String status) {
        this.idTagInfo = new IdTagInfo(null, null, status);
    }

    public IdTagInfo getIdTagInfo() {
        return idTagInfo;
    }

    public void setIdTagInfo(IdTagInfo idTagInfo) {
        this.idTagInfo = idTagInfo;
    }
}
