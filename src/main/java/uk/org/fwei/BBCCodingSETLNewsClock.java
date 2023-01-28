package uk.org.fwei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;

import uk.org.fwei.bbc_coding_setl_news_clock.InMemoryMessageRespository;
import uk.org.fwei.bbc_coding_setl_news_clock.Message;
import uk.org.fwei.bbc_coding_setl_news_clock.MessageRepository;

@SpringBootApplication // (scanBasePackageClasses = BBCCodingSETLNewsClock.class)
public class BBCCodingSETLNewsClock {

	@Bean
	public MessageRepository messageRepository() {
		return new InMemoryMessageRespository();
	}

	@Bean
	public Converter<String, Message> messageConverter() {
		return new Converter<String, Message>() {
			@Override
			public Message convert(String id) {
				return messageRepository().findMessage(Long.valueOf(id));
			}
		};
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BBCCodingSETLNewsClock.class, args);
	}
}
