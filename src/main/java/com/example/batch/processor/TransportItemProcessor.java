package com.example.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.example.batch.model.Transport;

public class TransportItemProcessor implements ItemProcessor<Transport, Transport> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportItemProcessor.class);

    @Override
    public Transport process(final Transport transportItem) {
        Transport processedItem = new Transport();
        processedItem.setMake(transportItem.getMake().toUpperCase());
        processedItem.setModel(transportItem.getModel().toUpperCase());
        processedItem.setTransportType(transportItem.getTransportType());
        processedItem.setYear(transportItem.getYear());
        processedItem.setOdometerReading(transportItem.getOdometerReading());

        LOGGER.info("Processed: " + processedItem.getMake() + ", " + processedItem.getModel());

        return processedItem;
    }

}
