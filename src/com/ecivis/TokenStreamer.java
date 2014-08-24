/*
Copyright 2014 eCivis, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.ecivis;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class TokenStreamer {

	public static final int BASE36 = 1;
	public static final int BASE64 = 2;
	public static final int BASE64_URLSAFE = 3;
	public static final int MIN_TOKEN_BYTE_SPACE = 1;
	public static final int MAX_TOKEN_BYTE_SPACE = 32;

	private SecureRandom sr;

	private int tokenByteSpace = 4;
	private int encoding = BASE36;
	private final int tokenBatchMax = 2000;

	public TokenStreamer() {
		try {
			// Apparently, it's bad practice to specify a provider since it would override user configuration.
			sr = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
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
			throw new Exception("Inexplicably, an invalid encoding was specified.");
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

	/*
	 * There's probably a good way to check whether this is valid without hard coding this.
	 */
	public void setEncoding(int encoding) throws Exception {
		if (encoding >= BASE36 && encoding <= BASE64_URLSAFE) {
			this.encoding = encoding;
		} else {
			throw new Exception("The specified encoding type is invalid.");
		}
	}

	public void setTokenByteSpace(int tokenByteSpace) throws Exception {
		if (tokenByteSpace >= MIN_TOKEN_BYTE_SPACE && tokenByteSpace <= MAX_TOKEN_BYTE_SPACE) {
			this.tokenByteSpace = tokenByteSpace;
		} else {
			throw new Exception("The tokenByteSpace specified is out of bounds.");
		}
	}

}
