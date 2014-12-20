package com.example.batch.model.mapper;

import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.example.batch.model.Transport;

public class TransportItemSqlParameterSourceProvider implements ItemSqlParameterSourceProvider<Transport> {

    @Override
    public SqlParameterSource createSqlParameterSource(Transport item) {

        MapSqlParameterSource source = new MapSqlParameterSource();

        source.addValue("transportType", item.getTransportType().toString());
        source.addValue("make", item.getMake());
        source.addValue("model", item.getModel());
        source.addValue("year", item.getYear());
        source.addValue("odometerReading", item.getOdometerReading());

        return source;
    }

}
