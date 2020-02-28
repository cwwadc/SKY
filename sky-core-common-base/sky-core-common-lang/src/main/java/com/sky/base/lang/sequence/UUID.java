package com.sky.base.lang.sequence;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;

import java.util.Arrays;

public final class UUID {

    private UUID() {}

    private static final RandomBasedGenerator uuidGenerator = Generators.randomBasedGenerator();

    // lookup is an array indexed by the **char**, and it has
    // valid values set with the decimal value of the hex char.
    private static final long[] lookup = buildLookup();
    private static final int DASH = -1;
    private static final int ERROR = -2;
    private static long[] buildLookup() {
        long [] lu = new long[128];
        Arrays.fill(lu, ERROR);
        lu['0'] = 0;
        lu['1'] = 1;
        lu['2'] = 2;
        lu['3'] = 3;
        lu['4'] = 4;
        lu['5'] = 5;
        lu['6'] = 6;
        lu['7'] = 7;
        lu['8'] = 8;
        lu['9'] = 9;
        lu['a'] = 10;
        lu['b'] = 11;
        lu['c'] = 12;
        lu['d'] = 13;
        lu['e'] = 14;
        lu['f'] = 15;
        lu['A'] = 10;
        lu['B'] = 11;
        lu['C'] = 12;
        lu['D'] = 13;
        lu['E'] = 14;
        lu['F'] = 15;
        lu['-'] = DASH;
        return lu;
    }

    // FROM STRING

    public static java.util.UUID fromString(final String str) {
        final int len = str.length();
        if (len != 36) {
            throw new IllegalArgumentException("Invalid UUID string (expected to be 36 characters long)");
        }
        final long[] vals = new long[2];
        int shift = 60;
        int index = 0;
        for (int i = 0; i < len; i++) {
            final int c = str.charAt(i);
            if (c >= lookup.length || lookup[c] == ERROR) {
                throw new IllegalArgumentException("Invalid UUID string (unexpected '" + str.charAt(i) + "' at position " + i + " -> " + str + " )");
            }

            if (lookup[c] == DASH) {
                if ((i - 8) % 5 != 0) {
                    throw new IllegalArgumentException("Invalid UUID string (unexpected '-' at position " + i + " -> " + str + " )");
                }
                continue;
            }
            vals[index] |= lookup[c] << shift;
            shift -= 4;
            if (shift < 0) {
                shift = 60;
                index++;
            }
        }
        return new java.util.UUID(vals[0], vals[1]);
    }

    // TO STRING

    // recode is 2-byte arrays representing the hex representation of every byte value (all 256)
    private static final char[][] recode = buildByteBlocks();
    private static char[][] buildByteBlocks() {
        final char[][] ret = new char[256][];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = String.format("%02x", i).toCharArray();
        }
        return ret;
    }

    public static String toString(final java.util.UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        char[] uuidChars = new char[36];
        int cursor = uuidChars.length;
        while (cursor > 24 ) {
            cursor -= 2;
            System.arraycopy(recode[(int)(lsb & 0xff)], 0, uuidChars, cursor, 2);
            lsb >>>= 8;
        }
        uuidChars[--cursor] = '-';
        while (cursor > 19) {
            cursor -= 2;
            System.arraycopy(recode[(int)(lsb & 0xff)], 0, uuidChars, cursor, 2);
            lsb >>>= 8;
        }
        uuidChars[--cursor] = '-';
        while (cursor > 14) {
            cursor -= 2;
            System.arraycopy(recode[(int)(msb & 0xff)], 0, uuidChars, cursor, 2);
            msb >>>= 8;
        }
        uuidChars[--cursor] = '-';
        while (cursor > 9) {
            cursor -= 2;
            System.arraycopy(recode[(int)(msb & 0xff)], 0, uuidChars, cursor, 2);
            msb >>>= 8;
        }
        uuidChars[--cursor] = '-';
        while (cursor > 0) {
            cursor -= 2;
            System.arraycopy(recode[(int)(msb & 0xff)], 0, uuidChars, cursor, 2);
            msb >>>= 8;
        }
        return new String(uuidChars);
    }

    public static java.util.UUID random() {
        return uuidGenerator.generate();
    }
    
    public static String randomString() {
    	return toString(random()).replaceAll("-", "").toUpperCase();
    }
}
