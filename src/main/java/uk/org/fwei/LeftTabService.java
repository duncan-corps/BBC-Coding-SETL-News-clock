package uk.org.fwei;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import uk.org.fwei.casparcg.server.AMCPCommand;
import uk.org.fwei.casparcg.server.Client;

/**
 * Encapsulates the defined configuration, state, and operations of the <b>Left
 * tab</b>. As soon as this {@link Component} has been constructed, it will send
 * <b>CG ADD</b> and <b>CG INVOKE</b> AMCP commands. Every second, checks
 * whether the time (<b>HH:MM</b>) portion should be changed, and sends a new
 * <b>CG INVOKE</b> if it has.
 * 
 * @author dpc
 */
@Component
public class LeftTabService {

	@Value("${leftTab.textPrefix:BBC News}")
	private String textPrefix;

	private static final long ONE_SECOND_IN_MILLISECONDS = 1000;
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

	private final AtomicInteger channel = new AtomicInteger(1);
	private final AtomicInteger cgLayer = new AtomicInteger(1);
	private final AtomicReference<String> state = new AtomicReference<>("on");
	private final AtomicReference<String> textSuffix = new AtomicReference<>(nowString());
	private final Client client;

	public LeftTabService(final Client client) {
		this.client = client;
	}

	@PostConstruct
	public void postConstruct() {
		add();
		invoke();
	}

	private String nowString() {
		final ZonedDateTime nowZonedDateTime = ZonedDateTime.now();
		final String nowString = nowZonedDateTime.format(dateTimeFormatter);

		return nowString;
	}

	public String add() {
		final String amcpCommand = AMCPCommand.cgAdd(channel.get(), cgLayer.get(), "main/MAIN", true);
		final String amcpResponse = client.send(amcpCommand);

		return amcpResponse;
	}

	public String invoke() {
		// TODO DPC:DPC Check state to decide whether to include the text
		final String method = "leftTab('%s', '%s %s')".formatted(state.get(), textPrefix, textSuffix.get());
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

	/**
	 * Update {@link #state} to the supplied new value (should be "on" or "off"),
	 * returning the old value for comparison.
	 * 
	 * @param newValue
	 * @return
	 */
	public String state(final String newValue) {
		final String oldValue = getAndSetState(state, newValue);

		return oldValue;
	}

	/**
	 * Update {@link #textPrefix} to the supplied new value, returning the old value
	 * for comparison.
	 * 
	 * @param newValue
	 * @return
	 */
	public String textSuffix(final String newValue) {
		final String oldValue = getAndSetState(textSuffix, newValue);

		return oldValue;
	}

	@Scheduled(initialDelay = ONE_SECOND_IN_MILLISECONDS, fixedDelay = ONE_SECOND_IN_MILLISECONDS)
	public void scheduled() {
		final String newTextSuffix = nowString();
		final String oldTextSuffix = textSuffix(newTextSuffix);

		// TODO DPC:DPC Could move this into textSuffix(String)
		if (!oldTextSuffix.equals(newTextSuffix)) {
			invoke();
		}
	}
}
