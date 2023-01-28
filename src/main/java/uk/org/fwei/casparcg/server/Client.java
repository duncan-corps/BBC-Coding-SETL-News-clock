package uk.org.fwei.casparcg.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Client {

	@Value("${casparCGServer.host:localhost}")
	private String host;
	
	public String send(final String amcpCommand) {
		System.out.println("send [%s] to %s".formatted(amcpCommand, host));

		return null;
	}
}
