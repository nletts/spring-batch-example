package com.example.batch;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.example.batch.config.DataConfig;
import com.example.batch.config.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TransportImportBatch.class, TestConfig.class },
        loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
public class TransportImportBatchTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    
    @Autowired
    private DataConfig dataConfig;
    
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    private JobParametersBuilder jobParametersBuilder;

    @Before
    public void setUp() throws Exception {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema-transport.sql"));
        populator.populate(dataConfig.dataSource().getConnection());
        
        jobParametersBuilder = new JobParametersBuilder();
    }
    
    @Test
    public void testValidatesJobParams() throws Exception {
        exception.expect(JobParametersInvalidException.class);
        exception.expectMessage("The JobParameters do not contain required keys: [fileName]");
        jobLauncherTestUtils.launchJob();
    }

    @Test
    public void testSuccess() throws Exception {
        jobParametersBuilder.addString("fileName", "sample-data.csv");

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParametersBuilder.toJobParameters());
        assertThat(ExitStatus.COMPLETED, equalTo(jobExecution.getExitStatus()));
    }

}
