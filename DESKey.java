public class DESKey {
    
    
    private static final int[] PC1 = {
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7,
        62, 54, 46, 38, 30, 22, 14, 6,
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        64, 56, 48, 40, 32, 24, 16, 8
    };
    
   
    private static final int[] PC2 = {
        14, 17, 11, 24, 1, 5, 3, 28, 
        15, 6, 21, 10, 23, 19, 12, 4, 
        26, 8, 16, 7, 27, 20, 13, 2, 
        41, 52, 31, 37, 47, 55, 30, 40, 
        51, 45, 33, 48, 44, 49, 39, 56, 
        34, 53, 46, 42, 50, 36, 29, 32
    };
    
   
    private static final int[] SHIFT_SCHEDULE = {
        1, 1, 2, 2, 2, 2, 1, 2,
        2, 2, 2, 2, 1, 2, 2, 1
    };

    
    public static long toLong(boolean[] bits) {
        long value = 0;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]) {
                value |= (1L << (bits.length - 1 - i));
            }
        }
        return value;
    }

    
    public static boolean[] toBits(long value, int size) {
        boolean[] bits = new boolean[size];
        for (int i = 0; i < size; i++) {
            bits[i] = (value & (1L << (size - 1 - i))) != 0;
        }
        return bits;
    }

    
    public static boolean[] permutePC1(boolean[] key) {
        boolean[] permutedKey = new boolean[56];
        for (int i = 0; i < 56; i++) {
            permutedKey[i] = key[PC1[i] - 1];
        }
        return permutedKey;
    }

   
    public static boolean[][] splitKey(boolean[] key) {
        boolean[] C = new boolean[28];
        boolean[] D = new boolean[28];
        System.arraycopy(key, 0, C, 0, 28);
        System.arraycopy(key, 28, D, 0, 28);
        return new boolean[][] {C, D};
    }

    public static boolean[] leftShift(boolean[] halfKey, int shift) {
        boolean[] shiftedKey = new boolean[28];
        for (int i = 0; i < 28; i++) {
            shiftedKey[i] = halfKey[(i + shift) % 28];
        }
        return shiftedKey;
    }

   
    public static boolean[] generateRoundKey(boolean[] C, boolean[] D) {
        boolean[] combined = new boolean[56];
        System.arraycopy(C, 0, combined, 0, 28);
        System.arraycopy(D, 0, combined, 28, 28);

        boolean[] roundKey = new boolean[48];
        for (int i = 0; i < 48; i++) {
            roundKey[i] = combined[PC2[i] - 1];
        }
        return roundKey;
    }

   
    public static boolean[][] generateKeys(boolean[] key) {
        boolean[] permutedKey = permutePC1(key);
        boolean[][] halves = splitKey(permutedKey);
        boolean[] C = halves[0];
        boolean[] D = halves[1];

        boolean[][] roundKeys = new boolean[16][48];
        for (int i = 0; i < 16; i++) {
            // Perform the shifts as per the schedule
            C = leftShift(C, SHIFT_SCHEDULE[i]);
            D = leftShift(D, SHIFT_SCHEDULE[i]);

            // Generate the round key using PC2
            roundKeys[i] = generateRoundKey(C, D);
        }
        return roundKeys;
    }

    public static void main(String[] args) {
     
        boolean[] key = new boolean[64];
        for (int i = 0; i < 64; i++) {
            key[i] = (i % 2 == 0); 
        }
        boolean[][] roundKeys = generateKeys(key);

        System.out.println("Generated round keys:");
        for (int i = 0; i < 16; i++) {
            System.out.println("Round " + (i + 1) + ": " + toLong(roundKeys[i]));
        }
    }
}
