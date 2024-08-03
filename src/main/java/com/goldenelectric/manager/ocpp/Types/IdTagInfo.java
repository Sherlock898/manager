package com.goldenelectric.manager.ocpp.Types;

public class IdTagInfo {
    private String expiryDate;
    private String parentIdTag;
    private String status;

    public IdTagInfo() {
    }

    public IdTagInfo(String expiryDate, String parentIdTag, String status) {
        this.expiryDate = expiryDate;
        this.parentIdTag = parentIdTag;
        this.status = status;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getParentIdTag() {
        return parentIdTag;
    }

    public void setParentIdTag(String parentIdTag) {
        this.parentIdTag = parentIdTag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
