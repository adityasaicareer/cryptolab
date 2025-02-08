public class AESnew {

    // AES S-box (substitution box)
    private static final byte[] S_BOX = {
        (byte)0x63, (byte)0x7C, (byte)0x77, (byte)0x7B, (byte)0xF2, (byte)0x6B, (byte)0x6F, (byte)0xC5,
        (byte)0x30, (byte)0x01, (byte)0x67, (byte)0x2B, (byte)0xFE, (byte)0xD7, (byte)0xAB, (byte)0x76,
        (byte)0xCA, (byte)0x82, (byte)0xC9, (byte)0x7D, (byte)0xFA, (byte)0x59, (byte)0x47, (byte)0xF0,
        (byte)0xAD, (byte)0xD4, (byte)0xA2, (byte)0xAF, (byte)0x9C, (byte)0xA8, (byte)0x51, (byte)0xA3,
        (byte)0x40, (byte)0x8F, (byte)0x92, (byte)0x9D, (byte)0x38, (byte)0xF5, (byte)0xBC, (byte)0xB6,
        (byte)0xDA, (byte)0x21, (byte)0x10, (byte)0xFF, (byte)0xF3, (byte)0xD2, (byte)0xCD, (byte)0x0C,
        (byte)0x13, (byte)0xEC, (byte)0x5F, (byte)0x97, (byte)0x44, (byte)0x17, (byte)0xC4, (byte)0xA7,
        (byte)0x7E, (byte)0x3D, (byte)0x64, (byte)0x5D, (byte)0x19, (byte)0x73, (byte)0x60, (byte)0x81,
        (byte)0x4F, (byte)0xDC, (byte)0x22, (byte)0x2A, (byte)0x90, (byte)0x88, (byte)0x46, (byte)0xEE,
        (byte)0xB8, (byte)0x14, (byte)0xDE, (byte)0x5E, (byte)0x0B, (byte)0xDB, (byte)0xE0, (byte)0x32,
        (byte)0x3A, (byte)0x0A, (byte)0x49, (byte)0x06, (byte)0x24, (byte)0x5C, (byte)0xC2, (byte)0xD3,
        (byte)0xAC, (byte)0x62, (byte)0x91, (byte)0x95, (byte)0xE4, (byte)0x79, (byte)0xE7, (byte)0xC3,
        (byte)0x1D, (byte)0xF6, (byte)0x6A, (byte)0x28, (byte)0xD6, (byte)0x1E, (byte)0x0F, (byte)0xB0,
        (byte)0x87, (byte)0xED, (byte)0xB3, (byte)0x2C, (byte)0x3F, (byte)0x0C, (byte)0x4A, (byte)0x7F
    };

    // Round constants (RCON)
    private static final byte[] RCON = {
        (byte)0x01, (byte)0x02, (byte)0x04, (byte)0x08, (byte)0x10, (byte)0x20, (byte)0x40, (byte)0x80,
        (byte)0x1B, (byte)0x36, (byte)0x6C, (byte)0xD8, (byte)0xAB, (byte)0x53, (byte)0xA6
    };

    // AES key expansion
    public static byte[][] keyExpansion(byte[] key) {
        byte[][] roundKeys = new byte[44][4]; // 44 words for AES-128 (176 bytes)

        // Step 1: Copy the original key into the first 4 words
        for (int i = 0; i < 4; i++) {
            roundKeys[i][0] = key[i * 4];
            roundKeys[i][1] = key[i * 4 + 1];
            roundKeys[i][2] = key[i * 4 + 2];
            roundKeys[i][3] = key[i * 4 + 3];
        }

        byte[] temp = new byte[4];
        int round = 1;

        // Step 2: Generate the rest of the round keys
        for (int i = 4; i < 44; i++) {
            // Step 2.1: Copy the previous word to temp
            System.arraycopy(roundKeys[i - 1], 0, temp, 0, 4);

            // Step 2.2: Every 4th word (i % 4 == 0), apply key schedule core
            if (i % 4 == 0) {
                // Rotate the word
                temp = rotWord(temp);
                // Substitute the word using S-box
                temp = subWord(temp);
                // XOR with RCON
                temp[0] ^= RCON[round++];
            }

            // Step 2.3: XOR the word with the word 4 positions before it
            for (int j = 0; j < 4; j++) {
                roundKeys[i][j] = (byte) (roundKeys[i - 4][j] ^ temp[j]);
            }
        }

        return roundKeys;
    }

    // Rotate the word (shift left by one byte)
    private static byte[] rotWord(byte[] word) {
        byte[] rotated = new byte[4];
        rotated[0] = word[1];
        rotated[1] = word[2];
        rotated[2] = word[3];
        rotated[3] = word[0];
        return rotated;
    }

    // Substitute the word using the AES S-box
    private static byte[] subWord(byte[] word) {
        byte[] substituted = new byte[4];
        for (int i = 0; i < 4; i++) {
            substituted[i] = S_BOX[word[i] & 0xFF]; // Use the S-box for substitution
        }
        return substituted;
    }

    // Print the round keys in hexadecimal format
    public static void printRoundKeys(byte[][] roundKeys) {
        System.out.println("Round keys:");
        for (int i = 0; i < 44; i++) {
            System.out.printf("Word %2d: ", i);
            for (int j = 0; j < 4; j++) {
                System.out.printf("%02X ", roundKeys[i][j]); // Prints each byte in 2-digit hexadecimal
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // 16-byte (128-bit) AES key
        byte[] key = {
            (byte)0x2b, (byte)0x7e, (byte)0x15, (byte)0x16,
            (byte)0x28, (byte)0xae, (byte)0xd2, (byte)0xa6,
            (byte)0xab, (byte)0xf7, (byte)0x97, (byte)0x75,
            (byte)0x46, (byte)0x20, (byte)0x63, (byte)0xed
        };

        // Perform AES key expansion
        byte[][] roundKeys = keyExpansion(key);

        // Print the round keys in hexadecimal format
        printRoundKeys(roundKeys);
    }
}
