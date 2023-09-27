package com.galaxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class MonthlyStatementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonthlyStatementApplication.class, args);
	}

}
