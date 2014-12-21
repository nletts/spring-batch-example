package com.example.batch;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.example.batch.config.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TransportImportBatch.class, TestConfig.class}, 
    loader=AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
public class TransportImportBatchIntegrationTest {
    
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws Exception {
        
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertThat(ExitStatus.COMPLETED, equalTo(jobExecution.getExitStatus()));
    }

}