package uk.org.fwei.bbc_coding_setl_news_clock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Component;

import uk.org.fwei.casparcg.server.AMCPCommand;
import uk.org.fwei.casparcg.server.Client;

@Component
public class LeftTabService {

	private final AtomicInteger channel = new AtomicInteger(1);
	private final AtomicInteger cgLayer = new AtomicInteger(1);
	private final AtomicReference<String> state = new AtomicReference<>("on");
	private final AtomicReference<String> textSuffix = new AtomicReference<>("HH:MM");
	private final Client client;

	public LeftTabService(final Client client) {
		this.client = client;
	}

	public String invoke() {
		final String method = "leftTab('%s', 'BBC News %s')".formatted(state.get(), textSuffix.get());
		final String amcpCommand = AMCPCommand.cgInvoke(channel.get(), cgLayer.get(), method);
		final String amcpResponse = client.send(amcpCommand);

		return amcpResponse;
	}

	public LeftTabModel model() {
		return new LeftTabModel(channel.get(), cgLayer.get(), state.get(), textSuffix.get());
	}

	public String getAndSetState(String newValue) {
		final String oldValue = state.getAndSet(newValue);

		return oldValue;
	}
}
