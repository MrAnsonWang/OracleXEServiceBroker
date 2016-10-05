package com.pivotal.cf.broker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.pivotal.cf.broker.config.AppConfig;

@SpringBootApplication
@Import(value={AppConfig.class})
public class OracleXeServiceBrokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OracleXeServiceBrokerApplication.class, args);
	}
}
