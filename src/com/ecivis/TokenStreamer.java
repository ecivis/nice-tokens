package com.ecivis;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public final class TokenStreamer {

	public static final int BASE36 = 1;
	public static final int BASE64 = 2;
	public static final int BASE64_URLSAFE = 3;

	private SecureRandom sr;

	private int tokenByteSpace = 4;
	private int encoding = BASE36;
	private final int tokenBatchMax = 2000;

	public TokenStreamer() {
		try {
			sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		} catch (NoSuchAlgorithmException e) {
			System.out.print(e.getMessage());
		} catch (NoSuchProviderException e) {
			System.out.print(e.getMessage());
		}
	}

	public String getToken() throws Exception {
		byte[] randomBytes = new byte[tokenByteSpace]; 
		sr.nextBytes(randomBytes);

		if (this.encoding == BASE36) {
			return BareMinimumEncoder.encodeBase36(randomBytes);
		} else if (this.encoding == BASE64) {
			return BareMinimumEncoder.encodeBase64(randomBytes, false);
		} else if (this.encoding == BASE64_URLSAFE) {
			return BareMinimumEncoder.encodeBase64(randomBytes, true);
		} else {
			throw new Exception("Invalid encoding selected.");
		}
	}

	public String getTokens(int count) throws Exception {
		int _count = (count > 0 && count <= tokenBatchMax) ? count : 1;
		StringBuffer tokens = new StringBuffer();
		for (int i = 0; i < _count; i++) {
			if (i > 0) {
				tokens.append("," + getToken());
			} else {
				tokens.append(getToken());
			}
		}
		return tokens.toString();
	}

	/*
	 * Just in case somebody wants to set a specific seed.
	 * Typically, the source of seed material is SecureRandom.getSeed(int numBytes)
	 */
	public void reseed (byte[] seed) {
		sr.setSeed(seed);
	}

	public int getEncoding() {
		return encoding;
	}

	public void setEncoding(int encoding) {
		this.encoding = encoding;
	}

	public int getTokenByteSpace() {
		return tokenByteSpace;
	}

	public void setTokenByteSpace(int tokenByteSpace) {
		this.tokenByteSpace = tokenByteSpace;
	}

}
