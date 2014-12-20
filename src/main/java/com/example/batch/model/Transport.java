package com.example.batch.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Transport {

    public enum TransportType {
        CAR, TRUCK, MOTOCYCLE
    }

    @NotNull
    private TransportType transportType;

    @NotNull
    @Size(max = 30)
    private String make;

    @NotNull
    @Size(max = 30)
    private String model;

    @NotNull
    @Size(max = 4)
    private String year;

    @NotNull
    @Min(0)
    @Max(1000000)
    private Integer odometerReading;

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getOdometerReading() {
        return odometerReading;
    }

    public void setOdometerReading(Integer odometerReading) {
        this.odometerReading = odometerReading;
    }
}
