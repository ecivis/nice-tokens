Nice Tokens
===========

This is a tiny Java project with purpose of generating tokens that have the following properties:
* Varying length

    In contrast to hash output or a UUID with a constant length, _nice tokens_ are expressed with only as many character as required to represent the token value. The actual length is random and depends on the number of bytes used to generate a random value -- what I call the "token byte space." Cryptographers probably have a proper name for this, and perhaps they'll chime in.
* Properly randomized

    The PRNG used in the project is the `SHA1PRNG` algorithm from the default provider, accessed using `SecureRandom`. The implementation from the Sun provider is fast enough and well seeded with an OS entropy source. It's good enough.
* Short

    Given a token byte space of 8 bytes, resulting token values encoded with base 36 won't exceed 13 characters. They could be more compactly encoded with Base64, that has some other considerations.
* Web friendly

    Two built-in encoders (`BASE36` and `BASE64_URLSAFE`) produce token strings that can be placed in a URL without worry.

The itch Nice Token serves to scratch is as a source of tokens for a short URL redirection service. A batch of tokens are created in advance, validated for uniqueness, and saved for future use. In practice, a big batch of tokens are sent as one argument via JDBC to a stored procedure that maintains the token pool. The redirection service then issues one token as requested, updating the values for destination, click count, expiration, and so forth.


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

In both of the examples above, a list of tokens similar to the following is returned:

```
6k9legrunwsa,1qiy7g5njq8ym,wz3rj8mn5ma4,gysv0b5l9d45,1afqyi23rasue,nbc4zu5wg29i,1t4v0peumdxx9,wh8zjme38r9o,16dqdouyyzcp4,1seg03nmpcyai
```

Dependencies
------------

I've tried to make Nice Tokens without any external dependencies. In my testing, a stock Java 7 install on OS X worked without supplying any additional libraries. Please try it out and report any issues found.

Details
-------

I've tried to write a tool that works properly and is simple to use for a variety of purposes. However, there are some implementation details that I think are important. The `TokenStreamer` works in two basic ways after some quantity of random bytes has been fetched:
* Building a `BigInteger` and expressing the number to a radix of 36.
* Transforming the bytes array into a Base64 stream

In the first case, we lose one bit of token byte space due to discarding the sign in the leftmost position that makes the effective token byte space half as big. It's an insignificant difference when choosing more than a few bytes, but I feel obligated to mention it. There is probably a cool Java trick to get around this. In the operational mode where a byte array is converted straight to binary encoding, this bit loss does not happen.

License
-------

This open source project is licensed as Apache License 2.0. 
