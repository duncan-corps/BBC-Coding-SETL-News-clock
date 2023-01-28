package uk.org.fwei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BBCCodingSETLNewsClock {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BBCCodingSETLNewsClock.class, args);
	}
}
