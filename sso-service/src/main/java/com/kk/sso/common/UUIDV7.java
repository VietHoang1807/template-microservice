package com.kk.sso.common;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.UUID;

public class UUIDV7 {
    private static final SecureRandom random = new SecureRandom();

    public static UUID randomUUID() {
        byte[] value = randomByte();
        System.out.println("randomByte() ::" + Arrays.toString(value));
        ByteBuffer buf = ByteBuffer.wrap(value);
        System.out.println("ByteBuffer.wrap(value) ::" + Arrays.toString(buf.array()));
        long high = buf.getLong();
        long low = buf.getLong();
        System.out.println("ByteBuffer.wrap(low) ::" + high);
        System.out.println("ByteBuffer.wrap(high) ::" + low);

        return new UUID(high, low);
    }

    private static byte[] randomByte() {
        // random bytes
        byte[] value = new byte[16];
        random.nextBytes(value);
        System.out.println("value[16] ::"+ Arrays.toString(value));
        
        // current timestamp in ms
        ByteBuffer timestamp = ByteBuffer.allocate(Long.BYTES);
        timestamp.putLong(System.currentTimeMillis());
        System.out.println("timestamp.array() ::"+ Arrays.toString(timestamp.array()));

        // timestamp
        System.arraycopy(timestamp.array(), 2, value, 0, 6);

        // version and variant
        value[6] = (byte) ((value[6] & 0x0f) | 0x70);
        value[8] = (byte) ((value[8] & 0x3f) | 0x80);
        return value;
    }

    public static void main(String[] args) {
        var uuid = randomUUID();
        System.out.println(uuid);
    }
}
