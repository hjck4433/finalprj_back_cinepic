package com.kh.cinepic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CinepicApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinepicApplication.class, args);
	}

}
