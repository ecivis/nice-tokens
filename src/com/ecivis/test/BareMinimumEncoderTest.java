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

package com.ecivis.test;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;

import com.ecivis.BareMinimumEncoder;

public class BareMinimumEncoderTest {

	@Test
	public void testEncodeBase36() {
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.putInt(387420489);
		byte[] input = bf.array();
		assertEquals("6enrk9", BareMinimumEncoder.encodeBase36(input));

		byte[] zeros = {0, 0, 0, 0};
		assertEquals("0", BareMinimumEncoder.encodeBase36(zeros));
	}

	@Test
	public void testEncodeBase64() {
		byte[] input = {-120, 67, -41, -7, 36, 22, 33, 29, -23, -21, -71, 99, -1, 76, -30, -127, 37, -109, 40, 120};
		assertEquals("iEPX+SQWIR3p67lj/0zigSWTKHg=", BareMinimumEncoder.encodeBase64(input, false));
		assertEquals("iEPX-SQWIR3p67lj_0zigSWTKHg~", BareMinimumEncoder.encodeBase64(input, true));

		byte[] zeros = {0, 0, 0, 0};
		assertEquals("AAAAAA==", BareMinimumEncoder.encodeBase64(zeros, false));
	}
	
}
