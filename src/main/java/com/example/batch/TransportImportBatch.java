package com.example.batch;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

import com.example.batch.config.StandaloneInfrastructureConfiguration;
import com.example.batch.model.Transport;
import com.example.batch.model.mapper.TransportFieldMapper;
import com.example.batch.model.mapper.TransportItemSqlParameterSourceProvider;
import com.example.batch.processor.TransportItemProcessor;
import com.example.batch.validator.BeanValidator;

@Configuration
@EnableBatchProcessing
@Import(StandaloneInfrastructureConfiguration.class)
public class TransportImportBatch {

    @Bean
    public ItemReader<Transport> reader() {
        FlatFileItemReader<Transport> reader = new FlatFileItemReader<Transport>();
        reader.setResource(new ClassPathResource("sample-data.csv"));
        reader.setLineMapper(new DefaultLineMapper<Transport>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[] { "transportType", "make", "model", "year", "odometerReading" });
                    }
                });
                /*
                 * setFieldSetMapper(new BeanWrapperFieldSetMapper<Transport>()
                 * {{ setTargetType(Transport.class); }});
                 */
                // Use customer mapper because of TransportType enum
                setFieldSetMapper(new TransportFieldMapper());
            }
        });

        return reader;
    }

    @Bean
    public ItemProcessor<Transport, Transport> processor(Validator<Transport> itemValidator) {
        ValidatingItemProcessor<Transport> validatingProcessor = new ValidatingItemProcessor<Transport>();
        validatingProcessor.setValidator(itemValidator);
        TransportItemProcessor personProcessor = new TransportItemProcessor();

        CompositeItemProcessor<Transport, Transport> compositeProcessor =
                new CompositeItemProcessor<Transport, Transport>();

        List<ItemProcessor<Transport, Transport>> itemProcessors = new ArrayList<ItemProcessor<Transport, Transport>>();
        itemProcessors.add(validatingProcessor);
        itemProcessors.add(personProcessor);

        compositeProcessor.setDelegates(itemProcessors);

        return compositeProcessor;
    }

    @Bean
    public ItemWriter<Transport> writer(DataSource dataSource) {
        JdbcBatchItemWriter<Transport> writer = new JdbcBatchItemWriter<Transport>();
        // writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Transport>());
        // Use customer provider because of TransportType enum
        writer.setItemSqlParameterSourceProvider(new TransportItemSqlParameterSourceProvider());
        writer.setSql("INSERT INTO transport (transport_type, make, model, year, odometer_reading) "
                + "VALUES (:transportType, :make, :model, :year, :odometerReading)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public Validator<Transport> itemValidator() {
        return new BeanValidator<Transport>();
    }

    @Bean
    public Job loadTransportJob(JobBuilderFactory jobs, Step s1) {
        return jobs.get("loadTransportJob")
                .incrementer(new RunIdIncrementer())
                .flow(s1)
                .end()
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Transport> reader,
            ItemWriter<Transport> writer, ItemProcessor<Transport, Transport> processor) {
        return stepBuilderFactory.get("step1")
                .<Transport, Transport> chunk(10)
                .reader(reader)
                .processor(processor)
                .faultTolerant()
                .skip(ValidationException.class).skipLimit(10)
                .writer(writer)
                .build();
    }

}
