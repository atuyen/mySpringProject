package com.seasolutions.stock_management;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication()
@EnableScheduling
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(com.seasolutions.stock_management.Application.class, args);
	}


}
