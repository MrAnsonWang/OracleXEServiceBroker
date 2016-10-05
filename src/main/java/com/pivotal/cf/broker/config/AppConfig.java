package com.pivotal.cf.broker.config;

import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.pivotal.cf.broker")
@EnableJpaRepositories(basePackages = "com.pivotal.cf.broker")
public class AppConfig {
	
	@Autowired
	private Environment env;
	
	@Configuration
	@Profile("default")
	class LocalConfig {
    	
		@Bean
		@Primary
		public DataSource dataSource() throws Exception {
			
			PoolProperties p = new PoolProperties();
			p.setDriverClassName(env.getProperty("metadata.datasource.driverClassName"));
			p.setUsername(env.getProperty("metadata.datasource.username"));
			p.setPassword(env.getProperty("metadata.datasource.password"));
			p.setUrl(env.getProperty("metadata.datasource.url"));
			p.setMaxActive(Integer.valueOf(env.getProperty("metadata.datasource.max-active")));
			p.setMinIdle(Integer.valueOf(env.getProperty("metadata.datasource.min-idle")));
			p.setMaxIdle(Integer.valueOf(env.getProperty("metadata.datasource.max-idle")));
			p.setInitialSize(Integer.valueOf(env.getProperty("metadata.datasource.initial-size")));
			org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
			ds.setPoolProperties(p);
			return ds;
			
		}
		
	}
	
	@Configuration
	@Profile("cloud")
	class CloudConfig extends AbstractCloudConfig {

		@Bean
		@Primary
		public DataSource dataSource() {
			return connectionFactory().dataSource("oraclexesvcbrk-metadata-database");
		}

		/*
		@Bean(name = "managedDataSource")
		public DataSource managedDataSource() {
			return connectionFactory().dataSource("oraclexesvcbrk-managed-database");
		}*/
		
	}
	
	@Bean(name = "managedDataSource")
	public DataSource managedDataSource() throws Exception {
		
		PoolProperties p = new PoolProperties();
		p.setDriverClassName(env.getProperty("managed.datasource.driverClassName"));
		p.setUsername(env.getProperty("managed.datasource.username"));
		p.setPassword(env.getProperty("managed.datasource.password"));
		p.setUrl(env.getProperty("managed.datasource.url"));
		p.setMaxActive(Integer.valueOf(env.getProperty("managed.datasource.max-active")));
		p.setMinIdle(Integer.valueOf(env.getProperty("managed.datasource.min-idle")));
		p.setMaxIdle(Integer.valueOf(env.getProperty("managed.datasource.max-idle")));
		p.setInitialSize(Integer.valueOf(env.getProperty("managed.datasource.initial-size")));
		org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
		ds.setPoolProperties(p);
		return ds;
	
	}

}
