package com.example.batch.processor;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Before;
import org.junit.Test;

import com.example.batch.model.Transport;
import com.example.batch.model.Transport.TransportType;

public class TransportItemProcessorTest {
    
    private TransportItemProcessor processor;
    
    @Before
    public void setUp() {
        processor = new TransportItemProcessor();
    }

    @Test
    public void testProcess() throws Exception {
        Transport transport = new Transport();
        transport.setTransportType(TransportType.CAR);
        transport.setMake("Porche");
        transport.setModel("Turbo 911");
        transport.setYear("2009");
        transport.setOdometerReading(100);
        
        Transport processedTransport = processor.process(transport);
        
        //Check main processed fields
        assertThat(processedTransport.getMake(), equalTo(transport.getMake().toUpperCase()));
        assertThat(processedTransport.getModel(), equalTo(transport.getModel().toUpperCase()));
        
        //Check other fields haven't changed
        assertThat(processedTransport.getTransportType(), equalTo(transport.getTransportType()));
        assertThat(processedTransport.getYear(), equalTo(transport.getYear()));
        assertThat(processedTransport.getOdometerReading(), equalTo(transport.getOdometerReading()));
    }

}
