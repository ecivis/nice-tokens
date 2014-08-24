package com.ecivis.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ecivis.TokenStreamer;

public class TokenStreamerTest {

	@Test
	public void testTokens() {
		TokenStreamer ts = new TokenStreamer();
		String token;
		String[] tokens;

		try {
			ts.setEncoding(TokenStreamer.BASE36);
			ts.setTokenByteSpace(4);
			token = ts.getToken();
			assertTrue(token.length() > 0);
			assertFalse(token.indexOf("-") > 0);
			
			ts.setEncoding(TokenStreamer.BASE64_URLSAFE);
			ts.setTokenByteSpace(16);
			token = ts.getToken();
			assertTrue(token.length() > 0);
			assertTrue(token.indexOf("=") < 0);
			assertTrue(token.indexOf("+") < 0);
			assertTrue(token.indexOf("/") < 0);

			ts.setEncoding(TokenStreamer.BASE64);
			ts.setTokenByteSpace(4);
			tokens = ts.getTokens(100).split(",");
			assertTrue("Expected 100 tokens.", tokens.length == 100);

			tokens = ts.getTokens(1000000).split(",");
			assertTrue("Expected 1 token because this exceeds the batch max.", tokens.length == 1);
		} catch (Exception e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	public void testBadSeedTokens() {
		TokenStreamer ts = new TokenStreamer();
		byte[] seed = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		ts.reseed(seed);
		String token;

		try {
			ts.setTokenByteSpace(12);
			token = ts.getToken();
			assertEquals("13se8zdver4axceam9w", token);

		} catch (Exception e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}
	
}

