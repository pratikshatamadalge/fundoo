package com.bridgelabz.fundoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author Pratiksha Tamadalge
 *
 */
@EnableCaching
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackages = { "com.bridgelabz.fundoo", "com.bridgelabz.fundoo.note" })
@EnableMongoRepositories({ "com.bridgelabz.fundoo.note.repository", "com.bridgelabz.fundoo.repository" })
public class FundooMongoApplication {
	public static void main(String[] args) {
		SpringApplication.run(FundooMongoApplication.class, args);
	}
}
