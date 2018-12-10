package czegarram.demo.opms.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "czegarram.demo.opms")
@EnableAutoConfiguration
public class OpinionesApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpinionesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(OpinionesApplication.class, args);
	}

}

