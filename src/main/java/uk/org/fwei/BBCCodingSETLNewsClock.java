package uk.org.fwei;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication // (scanBasePackageClasses = BBCCodingSETLNewsClock.class)
public class BBCCodingSETLNewsClock {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BBCCodingSETLNewsClock.class, args);
	}

	@Bean
	public AtomicReference<String> leftTabState() {
		final AtomicReference<String> leftTabState = new AtomicReference<>("on");

		return leftTabState;
	}
}
