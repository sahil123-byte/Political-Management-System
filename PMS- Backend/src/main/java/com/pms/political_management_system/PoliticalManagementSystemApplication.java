package com.pms.political_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// @EnableScheduling powers the OutboxPublisher (see notification/outbox
// package) - the poller that reads unpublished rows from the transactional
// outbox and publishes them to Kafka.
@SpringBootApplication
@EnableScheduling
public class PoliticalManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoliticalManagementSystemApplication.class, args);
	}
}