package com.ecivis;

public class CommandLineInterface {

	public static void main(String[] args) {

		if (args.length < 2) {
			usage();
			System.exit(1);
		}

		String encoder = args[0];
		String byteSpace = args[1];
		int count = 1;
		if (args.length > 2) {
			count = Integer.parseInt(args[2]);
		}
		
		try {
			TokenStreamer ts = new TokenStreamer();
			if (encoder.compareToIgnoreCase("base36") == 0) {
				ts.setEncoding(TokenStreamer.BASE36);
			} else if (encoder.compareToIgnoreCase("base64") == 0) {
				ts.setEncoding(TokenStreamer.BASE64);
			} else if (encoder.compareToIgnoreCase("base64UrlSafe") == 0) {
				ts.setEncoding(TokenStreamer.BASE64_URLSAFE);
			}
			
			int tokenByteSpace = Integer.parseInt(byteSpace);
			if (tokenByteSpace >= TokenStreamer.MIN_TOKEN_BYTE_SPACE && tokenByteSpace <= TokenStreamer.MAX_TOKEN_BYTE_SPACE) {
				ts.setTokenByteSpace(tokenByteSpace);
			}

			if (count > 1) {
				System.out.println(ts.getTokens(count));
			} else {
				System.out.println(ts.getToken());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		System.exit(0);
	}
	
	private static void usage() {
		System.out.println("Nice Tokens Command Line Interface Usage:");
		System.out.println("  java -jar ecivis-nice-tokens.jar encoder byteSpace [count]");
		System.out.println("  Example: java -jar ecivis-nice-tokens.jar base36 4");
	}

}
