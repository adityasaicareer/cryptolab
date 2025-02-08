public class AESKeyExpansion {

    // AES S-box for SubWord operation
    private static final byte[] SBOX = {
        (byte)0x63, (byte)0x7c, (byte)0x77, (byte)0x7b, (byte)0xf2, (byte)0x6b, (byte)0x6f, (byte)0xc5,
        (byte)0x30, (byte)0x01, (byte)0x67, (byte)0x2b, (byte)0xfe, (byte)0xd7, (byte)0xab, (byte)0x76,
        (byte)0xca, (byte)0x82, (byte)0xc9, (byte)0x7d, (byte)0xfa, (byte)0x59, (byte)0x47, (byte)0xf0,
        (byte)0xad, (byte)0xd4, (byte)0xa2, (byte)0xaf, (byte)0x9c, (byte)0xa8, (byte)0x51, (byte)0xa3,
        (byte)0x40, (byte)0x8f, (byte)0x92, (byte)0x9d, (byte)0x38, (byte)0xf1, (byte)0xe0, (byte)0xfe,
        (byte)0xdb, (byte)0x67, (byte)0x79, (byte)0x32, (byte)0x8e, (byte)0x79, (byte)0x03, (byte)0x8e,
        (byte)0xe1, (byte)0x0f, (byte)0x73, (byte)0x7e, (byte)0x04, (byte)0x88, (byte)0x2c, (byte)0x4e
    };

    // Round constants (Rcon)
    private static final byte[] RCON = {
        (byte)0x00, (byte)0x01, (byte)0x02, (byte)0x04, (byte)0x08, (byte)0x10, (byte)0x20, (byte)0x40,
        (byte)0x80, (byte)0x1b, (byte)0x36
    };

    // Perform RotWord operation (rotate left by 1 byte)
    private static byte[] rotWord(byte[] word) {
        byte[] rotated = new byte[4];
        System.arraycopy(word, 1, rotated, 0, 3);
        rotated[3] = word[0];
        return rotated;
    }

    // Perform SubWord operation (substitute each byte using the S-box)
    private static byte[] subWord(byte[] word) {
        byte[] substituted = new byte[4];
        for (int i = 0; i < 4; i++) {
            substituted[i] = SBOX[word[i] & 0xFF];
        }
        return substituted;
    }

    // AES Key Expansion (for AES-128)
    public static byte[][] keyExpansion(byte[] key) {
        byte[][] roundKeys = new byte[11][16];  // 11 round keys for AES-128
        System.arraycopy(key, 0, roundKeys[0], 0, key.length);  // Copy the original key as the first round key

        byte[] temp = new byte[4];
        
        for (int i = 1; i < 11; i++) {
            // Copy the last word of the previous round key (starting from index 12 to 15)
            System.arraycopy(roundKeys[i - 1], 12, temp, 0, 4);

            // Apply RotWord and SubWord to every 4th word
            if (i % 4 == 0) {
                temp = subWord(rotWord(temp));
                temp[0] ^= RCON[i / 4];  // Apply round constant
            }

            // XOR the previous round's word and the new word to create the current round key
            for (int j = 0; j < 4; j++) {
                roundKeys[i][j] = (byte)(roundKeys[i - 1][j] ^ temp[j]);
            }

            // Generate the next 3 words for the current round key
            for (int j = 1; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    roundKeys[i][j * 4 + k] = (byte)(roundKeys[i][(j - 1) * 4 + k] ^ roundKeys[i - 1][j * 4 + k]);
                }
            }
        }

        return roundKeys;
    }

    // Print round keys in hex format
    private static void printRoundKeys(byte[][] roundKeys) {
        for (int i = 0; i < roundKeys.length; i++) {
            System.out.print("Round Key " + i + ": ");
            for (int j = 0; j < roundKeys[i].length; j++) {
                System.out.printf("%02x ", roundKeys[i][j]);
            }
            System.out.println();
        }
    }

    // Main method to test the key expansion
    public static void main(String[] args) {
        // Example: 128-bit key (16 bytes)
        byte[] key = {
            (byte)0x2b, (byte)0x7e, (byte)0x15, (byte)0x16,
            (byte)0x28, (byte)0xae, (byte)0xd2, (byte)0xa6,
            (byte)0xab, (byte)0xf7, (byte)0x97, (byte)0x75,
            (byte)0x46, (byte)0x10, (byte)0x45, (byte)0x80
        };

        // Perform AES key expansion
        byte[][] roundKeys = keyExpansion(key);

        // Print the round keys
        printRoundKeys(roundKeys);
    }
}
