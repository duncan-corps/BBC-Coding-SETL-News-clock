package uk.org.fwei.casparcg.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AMCPCommandTest {

	@Test
	void cgAddTest() {
		final String actual = AMCPCommand.cgAdd(1, 1, "main/MAIN", true, null);
		assertEquals("CG 1 ADD 1 main/MAIN 1", actual);
	}

	@Test
	void cgInvokeOnTest() {
		final String actual = AMCPCommand.cgInvoke(1, 1, "leftTab('on', 'BBC NEWS HH:MM')");
		assertEquals("CG 1 INVOKE 1 \"leftTab('on', 'BBC NEWS HH:MM')\"", actual);
	}

	@Test
	void cgInvokeOffTest() {
		final String actual = AMCPCommand.cgInvoke(1, 1, "leftTab('off')");
		assertEquals("CG 1 INVOKE 1 \"leftTab('off')\"", actual);
	}
}
