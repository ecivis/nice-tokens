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

import java.math.BigInteger;

public class BareMinimumEncoder {

	private static final String BASE64_TABLE_STANDARD = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
	private static final String BASE64_TABLE_SAFE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_~";

	public static String encodeBase36(byte[] data) {
		BigInteger bi = new BigInteger(data);
		if (bi.signum() < 0) {
			return bi.negate().toString(36);
		} else {
			return bi.toString(36); 
		}
	}

	public static String encodeBase64(byte[] data, boolean urlSafe) {
		String s = "";
		String table = urlSafe ? BASE64_TABLE_SAFE : BASE64_TABLE_STANDARD;
		String pads = table.substring(64) + table.substring(64);

		int padding = (3 - (data.length % 3)) % 3;
		byte[] _data = new byte[data.length + padding];
		System.arraycopy(data, 0, _data, 0, data.length);
		for (int i = 0; i < _data.length; i += 3) {
			int j = ((_data[i] & 0xff) << 16) + ((_data[i + 1] & 0xff) << 8) + (_data[i + 2] & 0xff);
			s = s + table.charAt((j >> 18) & 0x3f) + table.charAt((j >> 12) & 0x3f) + table.charAt((j >> 6) & 0x3f) + table.charAt(j & 0x3f);
		}
		s = s.substring(0, s.length() - padding) + pads.substring(0, padding);
		return s;
	}
}
