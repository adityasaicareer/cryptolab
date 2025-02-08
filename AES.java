import java.util.Arrays;

public class AES {

    private static final int[][] SBOX = {
        {0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76},
        {0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0},
        {0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15},
        {0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75},
        {0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84},
        {0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF},
        {0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8},
        {0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2},
        {0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73},
        {0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB},
        {0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79},
        {0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08},
        {0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A},
        {0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E},
        {0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF},
        {0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16}
    };

    private static final int[] RCON = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1B, 0x36};

    public static void main(String[] args) {
        byte[] key = new byte[]{
            (byte) 0x2b, (byte) 0x7e, (byte) 0x15, (byte) 0x16,
            (byte) 0x28, (byte) 0xae, (byte) 0xd2, (byte) 0xa6,
            (byte) 0xab, (byte) 0xf7, (byte) 0xcf, (byte) 0x75,
            (byte) 0x04, (byte) 0x4c, (byte) 0x05, (byte) 0x61
        };

        byte[] plaintext = new byte[]{
            (byte) 0x32, (byte) 0x43, (byte) 0xf6, (byte) 0xa8,
            (byte) 0x88, (byte) 0x5a, (byte) 0x30, (byte) 0x8d,
            (byte) 0x31, (byte) 0x31, (byte) 0x98, (byte) 0xa2,
            (byte) 0xe0, (byte) 0x37, (byte) 0x07, (byte) 0x34
        };

        byte[] expandedKey = expandKey(key);
        System.out.println("Expanded Key:");
        for (int i = 0; i < expandedKey.length; i += 16) {
            System.out.println(Arrays.toString(Arrays.copyOfRange(expandedKey, i, i + 16)));
        }

        byte[] encryptedText = encrypt(plaintext, expandedKey);
        System.out.println("Encrypted Text:");
        System.out.println(Arrays.toString(encryptedText));
    }

    private static byte[] expandKey(byte[] key) {
        int keyLength = key.length;
        int expandedKeyLength = 176; // 16 bytes * (10 + 1 rounds)
        byte[] expandedKey = new byte[expandedKeyLength];
        System.arraycopy(key, 0, expandedKey, 0, keyLength);

        int bytesGenerated = keyLength;
        int rconIndex = 0;
        byte[] temp = new byte[4];

        while (bytesGenerated < expandedKeyLength) {
            System.arraycopy(expandedKey, bytesGenerated - 4, temp, 0, 4);

            if (bytesGenerated % keyLength == 0) {
                temp = scheduleCore(temp, rconIndex++);
            }

            for (int i = 0; i < 4; i++) {
                expandedKey[bytesGenerated] = (byte) (expandedKey[bytesGenerated - keyLength] ^ temp[i]);
                bytesGenerated++;
            }
        }

        return expandedKey;
    }

    private static byte[] scheduleCore(byte[] word, int rconIndex) {
        byte temp = word[0];
        word[0] = word[1];
        word[1] = word[2];
        word[2] = word[3];
        word[3] = temp;

        for (int i = 0; i < 4; i++) {
            word[i] = (byte) SBOX[(word[i] & 0xFF) >> 4][word[i] & 0x0F];
        }

        word[0] ^= RCON[rconIndex];
        return word;
    }

    private static byte[] encrypt(byte[] plaintext, byte[] expandedKey) {
        byte[] state = Arrays.copyOf(plaintext, plaintext.length);

        int rounds = 10;
        addRoundKey(state, Arrays.copyOfRange(expandedKey, 0, 16));

        for (int i = 1; i < rounds; i++) {
            subBytes(state);
            shiftRows(state);
            mixColumns(state);
            addRoundKey(state, Arrays.copyOfRange(expandedKey, i * 16, (i + 1) * 16));
        }

        subBytes(state);
        shiftRows(state);
        addRoundKey(state, Arrays.copyOfRange(expandedKey, rounds * 16, (rounds + 1) * 16));

        return state;
    }

    private static void subBytes(byte[] state) {
        for (int i = 0; i < state.length; i++) {
            state[i] = (byte) SBOX[(state[i] & 0xFF) >> 4][state[i] & 0x0F];
        }
    }

    private static void shiftRows(byte[] state) {
        byte[] temp = Arrays.copyOf(state, state.length);

        state[1] = temp[5];
        state[5] = temp[9];
        state[9] = temp[13];
        state[13] = temp[1];

        state[2] = temp[10];
        state[6] = temp[14];
        state[10] = temp[2];
        state[14] = temp[6];

        state[3] = temp[15];
        state[7] = temp[3];
        state[11] = temp[7];
        state[15] = temp[11];
    }

    private static void mixColumns(byte[] state) {
        for (int col = 0; col < 4; col++) {
            int start = col * 4;

            int s0 = state[start] & 0xFF;
            int s1 = state[start + 1] & 0xFF;
            int s2 = state[start + 2] & 0xFF;
            int s3 = state[start + 3] & 0xFF;

            state[start] = (byte) (mul2(s0) ^ mul3(s1) ^ s2 ^ s3);
            state[start + 1] = (byte) (s0 ^ mul2(s1) ^ mul3(s2) ^ s3);
            state[start + 2] = (byte) (s0 ^ s1 ^ mul2(s2) ^ mul3(s3));
            state[start + 3] = (byte) (mul3(s0) ^ s1 ^ s2 ^ mul2(s3));
        }
    }

    private static byte mul2(int x) {
        return (byte) (((x << 1) ^ (((x >> 7) & 1) * 0x1B)) & 0xFF);
    }

    private static byte mul3(int x) {
        return (byte) (mul2(x) ^ x);
    }

    private static void addRoundKey(byte[] state, byte[] roundKey) {
        for (int i = 0; i < state.length; i++) {
            state[i] ^= roundKey[i];
        }
    }
}