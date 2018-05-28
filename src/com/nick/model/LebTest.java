package com.nick.model;

/**
 * Created by KangShuai on 2018/5/22.
 */
public class LebTest {
    public static void main(String[] args) {
        byte[] bytes = new byte[5];
        writeSignedLeb128(bytes, 10000);
        for (byte b : bytes) {
            System.out.println(b);
        }
        bytes[0] = (byte) 0x04;
        bytes[1] = (byte) 0xe4;
        bytes[2] = 0;
        System.out.println(readUnsignedLeb128(bytes));

//        byte[] s = {(byte) 0xE4, (byte) 0xB8, (byte) 0x87, (byte) 0xE5, (byte) 0x91, (byte) 0xA8, (byte) 0xE4, (byte) 0xB8, (byte) 0x93, (byte) 0xE7, (byte) 0xBA, (byte) 0xBF};
        byte[] s = {(byte) 0xE6, (byte) 0x9B, (byte) 0xB9, (byte) 0xE8, (byte) 0xB7, (byte) 0xAF, (byte) 0x32, (byte) 0xE8, (byte) 0xB7, (byte) 0xAF};
        System.out.println(new String(s));
    }

    /**
     * Writes {@code value} as a signed integer to {@code out}, starting at
     * {@code offset}. Returns the number of bytes written.
     */
    public static void writeSignedLeb128(byte[] out, int value) {
        int remaining = value >> 7;
        boolean hasMore = true;
        int end = ((value & Integer.MIN_VALUE) == 0) ? 0 : -1;
        int pos = 0;
        while (hasMore) {
            hasMore = (remaining != end)
                    || ((remaining & 1) != ((value >> 6) & 1));

            out[pos] = ((byte) ((value & 0x7f) | (hasMore ? 0x80 : 0)));
            value = remaining;
            remaining >>= 7;
            pos++;
        }
    }

    /**
     * Reads an unsigned integer from {@code in}.
     */
    public static int readUnsignedLeb128(byte[] in) {
        int result = 0;
        int cur;
        int count = 0;

        do {
            cur = in[count] & 0xff;
            result |= (cur & 0x7f) << (count * 7);
            count++;
        } while (((cur & 0x80) == 0x80) && count < 5);

        if ((cur & 0x80) == 0x80) {
            return 0;
        }

        return result;
    }
}
