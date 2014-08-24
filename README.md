Nice Tokens
===========

This is a tiny Java project with goal of creating tokens that have the following properties:
* varying length

    In contrast to hash output with a constant length, _nice tokens_ are expressed with only as many character as required to represent the token value. The actual length is random and depends on the number of bytes used to generate a random value -- what I call the "token byte space." Cryptographers probably have a proper name for this, and perhaps they'll chime in.
* properly randomized

    The PRNG used in the project is the `SHA1PRNG` algorithm from the default provider, accessed using `SecureRandom`. The implementation from the Sun provider is fast enough and well seeded with an OS entropy source. It's good enough.
* short

    Given a token byte space of 8 bytes, resulting token values encoded with base 36 won't exceed 13 characters. They could be more compactly encoded with Base64, that has some other considerations.
* web friendly

    Two built-in encoders (`BASE36` and `BASE64_URLSAFE`) produce token strings that can be placed in a URL without worry.

Usage
-----

There are two ways to get some nice tokens: using `com.ecivis.TokenStreamer` or via the command line. Here's an example using Java to fetch 10 tokens, encoded with Base 36, from a token byte space of 8:

```java
TokenStreamer ts = new TokenStreamer();
ts.setEncoding(TokenStreamer.BASE36);
ts.setTokenByteSpace(8);
String tokens = ts.getTokens(10);
```

Viola. Now you have a comma-delimited list of tokens. You could do the same thing from the command line:

```bash
java -jar ecivis-nice-tokens-0.1.jar base36 8 10
```

Dependencies
------------

I've tried to make Nice Tokens without any external dependencies. In my testing, a stock Java 7 install on OS X worked without supplying any additional libraries.
