package uk.org.fwei.bbc_coding_setl_news_clock;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import uk.org.fwei.casparcg.server.AMCPCommand;
import uk.org.fwei.casparcg.server.Client;

@Component
public class LeftTabService {

	private static final long ONE_SECOND_IN_MILLISECONDS = 1000;
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

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

	private <V> V getAndSetState(AtomicReference<V> atomicReference, final V newV) {
		final V oldV = newV == null ? atomicReference.get() : atomicReference.getAndSet(newV);

		return oldV;
	}

	public String state(final String newValue) {
		final String oldValue = getAndSetState(state, newValue);

		return oldValue;
	}

	public String textSuffix(final String newValue) {
		final String oldValue = getAndSetState(textSuffix, newValue);

		return oldValue;
	}

	@Scheduled(initialDelay = ONE_SECOND_IN_MILLISECONDS, fixedDelay = ONE_SECOND_IN_MILLISECONDS)
	public void scheduled() {
		final ZonedDateTime nowZonedDateTime = ZonedDateTime.now();
		final String newTextSuffix = nowZonedDateTime.format(dateTimeFormatter);
		final String oldTextSuffix = getAndSetState(textSuffix, newTextSuffix);

		if (!oldTextSuffix.equals(newTextSuffix)) {
			invoke();
		}
	}
}
