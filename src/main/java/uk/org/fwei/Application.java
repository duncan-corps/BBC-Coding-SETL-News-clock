package uk.org.fwei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * Entry point for the app with nnotation-based configuratio of Spring Boot. Any
 * non-{@link Component} {@link Bean}s can be created here.
 * 
 * @author dpc
 */
@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}
