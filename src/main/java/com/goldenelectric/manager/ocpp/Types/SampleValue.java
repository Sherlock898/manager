package com.goldenelectric.manager.ocpp.Types;

public class SampleValue {
    private String value;
    private String context;
    private String format;
    private String measurand;
    private String phase;
    private String location;
    private String unit;

    public SampleValue() {}
    public SampleValue(String value, String context, String format, String measurand, String phase, String location, String unit) {
        this.value = value;
        this.context = context;
        this.format = format;
        this.measurand = measurand;
        this.phase = phase;
        this.location = location;
        this.unit = unit;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getMeasurand() {
        return measurand;
    }

    public void setMeasurand(String measurand) {
        this.measurand = measurand;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    
}
