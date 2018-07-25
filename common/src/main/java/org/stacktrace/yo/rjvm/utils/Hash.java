package org.stacktrace.yo.rjvm.utils;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;


public class Hash {

    private static final int myDefaultSeed = 1531361210;

    public static Hasher defaultFastHasher() {
        return fastHasher(myDefaultSeed);
    }

    public static Hasher fastHasher(int seed) {
        return Hashing.murmur3_32(seed)
                .newHasher();
    }

    public static String defaultFastHash(byte[] bytes) {
        return defaultFastHasher()
                .putBytes(bytes)
                .hash()
                .toString();
    }

}
