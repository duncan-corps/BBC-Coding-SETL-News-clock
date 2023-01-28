package uk.org.fwei.bbc_coding_setl_news_clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import uk.org.fwei.BBCCodingSETLNewsClock;

/**
 * Basic integration tests for demo application.
 * 
 * @author Dave Syer
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = BBCCodingSETLNewsClock.class)
//@WebAppConfiguration
//@IntegrationTest("server.port:0")
//@DirtiesContext
@SpringBootTest
@SpringJUnitConfig(BBCCodingSETLNewsClock.class)
public class BBCCodingSETLNewsClockTests {

	@Value("${local.server.port}")
	private int port;

	@Test
	public void testHome() throws Exception {
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity("http://localhost:" + this.port,
				String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		assertTrue(entity.getBody().contains("<title>Messages"),
				"Wrong body (title doesn't match):\n" + entity.getBody());
		assertFalse(entity.getBody().contains("layout:fragment"),
				"Wrong body (found layout:fragment):\n" + entity.getBody());
	}

	@Test
	public void testCreate() throws Exception {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.set("text", "FOO text");
		map.set("summary", "FOO");
		URI location = new TestRestTemplate().postForLocation("http://localhost:" + this.port, map);
		assertTrue(location.toString().contains("localhost:" + this.port), "Wrong location:\n" + location);
	}

	@Test
	public void testCss() throws Exception {
		ResponseEntity<String> entity = new TestRestTemplate()
				.getForEntity("http://localhost:" + this.port + "/css/bootstrap.min.css", String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		assertTrue(entity.getBody().contains("body"), "Wrong body:\n" + entity.getBody());
	}

}
