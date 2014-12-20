package com.example.batch.model.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.example.batch.model.Transport;
import com.example.batch.model.Transport.TransportType;

public class TransportFieldMapper implements FieldSetMapper<Transport> {

    @Override
    public Transport mapFieldSet(FieldSet fieldSet) throws BindException {
        Transport toMap = new Transport();
        toMap.setTransportType(TransportType.valueOf(fieldSet.readString("transportType").toUpperCase()));
        toMap.setMake(fieldSet.readString("make"));
        toMap.setModel(fieldSet.readString("model"));
        toMap.setYear(fieldSet.readString("year"));
        toMap.setOdometerReading(Integer.valueOf(fieldSet.readString("odometerReading")));
        return toMap;
    }

}
