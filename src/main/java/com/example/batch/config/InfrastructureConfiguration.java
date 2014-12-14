package com.example.batch.config;

import javax.sql.DataSource;

public interface InfrastructureConfiguration {

    DataSource dataSource();
	
}
