package com.goldenelectric.manager.ocpp.Types;

public class MeterValue {
    private String dateTime;
    private SampleValue[] sampleValues;

    public MeterValue(String dateTime, SampleValue[] sampleValues) {
        this.dateTime = dateTime;
        this.sampleValues = sampleValues;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public SampleValue[] getSampleValues() {
        return sampleValues;
    }

    public void setSampleValues(SampleValue[] sampleValues) {
        this.sampleValues = sampleValues;
    }
    
}
