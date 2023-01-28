package uk.org.fwei.casparcg.server;

import org.springframework.stereotype.Component;

@Component
public class Client {

	public String send(final String amcpCommand) {
		System.out.println("send [%s]".formatted(amcpCommand));

		return null;
	}
}
