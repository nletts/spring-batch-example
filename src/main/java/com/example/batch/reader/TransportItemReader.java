package com.example.batch.reader;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

import com.example.batch.model.Transport;
import com.example.batch.model.mapper.TransportFieldMapper;

public class TransportItemReader implements ItemReader<Transport> {

    private FlatFileItemReader<Transport> reader;

    //Needed if reading from the job parameters (in this case 'fileName')
    @BeforeStep
    public void initializeState(StepExecution stepExecution) {
        reader = new FlatFileItemReader<Transport>();
        reader.setResource(new ClassPathResource(stepExecution.getJobParameters().getString("fileName")));
        reader.setLineMapper(new DefaultLineMapper<Transport>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[] { "transportType", "make", "model", "year", "odometerReading" });
                    }
                });
                setFieldSetMapper(new TransportFieldMapper());
            }
        });
        reader.open(stepExecution.getExecutionContext());
    }

    @Override
    public Transport read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return reader.read();
    }

}
