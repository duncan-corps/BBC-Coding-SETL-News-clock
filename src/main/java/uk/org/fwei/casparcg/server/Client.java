package uk.org.fwei.casparcg.server;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Very minimal CasparCG Server client. Contains almost no error handling. Uses
 * {@link PrintWriter#format(String, Object...)} to appropriately format and
 * write supplied AMCP commands to and {@link BufferedReader#readLine()} to read
 * them from a {@link Socket}. Does not handle multiline responses. Does use the
 * REQ/RES mechanism to keep commands and responses synchronised.
 * 
 * @implNote {@link BufferedReader#readLine()} matches the semantics of AMCP's
 *           responses - that they're lines terminated by <b>\r\n</b>.
 * @author dpc
 */
@Component
public class Client implements Closeable {

	// Format REQ and RES identifiers as 6 hexadecimal digits, zero padded, to cover
	// a wide range of values in a concise space.
	private static final String IDENTIFIER_FORMAT = "%06x";
	private static final AtomicInteger identifierAtomicInteger = new AtomicInteger();

	private Socket socket = null;
	private PrintWriter printWriter = null;
	private BufferedReader bufferedReader = null;

	@Value("${casparCGServer.host:localhost}")
	private String host;

	@Value("${casparCGServer.port:5250}")
	private int port;

	private boolean connected() {
		if ("null".equals(host)) {
			socket = null;
		} else if (socket == null || !socket.isConnected() || socket.isClosed()) {
			try {
				socket = new Socket(host, port);
			} catch (final IOException ioException) {
				ioException.printStackTrace();
			}
		}

		if (socket != null && socket.isConnected() && !socket.isClosed()) {
			if (printWriter == null || bufferedReader == null) {
				try {
					final OutputStream outputStream = socket.getOutputStream();
					final InputStream inputStream = socket.getInputStream();
					final boolean autoFlush = true;
					printWriter = new PrintWriter(outputStream, autoFlush, StandardCharsets.UTF_8);
					final Reader reader = new InputStreamReader(inputStream);
					bufferedReader = new BufferedReader(reader);
				} catch (final IOException ioException) {
					ioException.printStackTrace();
				}
			}
		} else {
			printWriter = null;
			bufferedReader = null;
		}

		return printWriter != null && bufferedReader != null;
	}

	public String send(final String amcpCommand) {
		String amcpResponse = null;

		synchronized (identifierAtomicInteger) {
			if (connected()) {
				final int identifier = identifierAtomicInteger.getAndIncrement();
				printWriter.format("REQ " + IDENTIFIER_FORMAT + " %s\r\n", identifier, amcpCommand.trim());
				final String amcpResponsePrefix = "RES " + IDENTIFIER_FORMAT + " ".formatted(identifier);

				do {
					try {
						amcpResponse = bufferedReader.readLine();
					} catch (final IOException ioException) {
						ioException.printStackTrace();
					}
				} while (amcpResponse != null && !amcpResponse.startsWith(amcpResponsePrefix));
			} else {
				System.out.println("Cannot send [%s] to %s:%d".formatted(amcpCommand, host, port));
			}
		}

		return amcpResponse;
	}

	@Override
	public void close() throws IOException {
		synchronized (socket) {
			if (socket != null && !socket.isClosed()) {
				Stream.of(bufferedReader, printWriter, socket).filter(Objects::nonNull).forEach(closeable -> {
					try {
						closeable.close();
					} catch (final IOException ioException) {
						// Nothing to do
					}
				});

				bufferedReader = null;
				printWriter = null;
				socket = null;
			}
		}
	}
}
