import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class NonceFinder {
    public static void main(String[] args) {
        int difficulty = 4; // The number of leading zeros the hash must have
        String blockData = "Some block data here"; // The block of transactions to be hashed
        long nonce = 0; // The initial nonce value
        
        while (true) {
            // Combine the block data and nonce into a single string
            String dataWithNonce = blockData + nonce;
            
            try {
                // Hash the combined data using the SHA-256 algorithm
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(dataWithNonce.getBytes());
                
                // Check if the hash meets the difficulty target
                if (isDifficultyMet(hash, difficulty)) {
                    System.out.println("Nonce found: " + nonce);
                    System.out.println("Hash: " + bytesToHex(hash));
                    break;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            
            nonce++; // Increment the nonce for the next iteration
        }
    }
    
    // Checks if the hash has the specified number of leading zeros
    private static boolean isDifficultyMet(byte[] hash, int difficulty) {
        byte[] leadingZeros = new byte[difficulty];
        Arrays.fill(leadingZeros, (byte) 0);
        return Arrays.equals(Arrays.copyOf(hash, difficulty), leadingZeros);
    }
    
    // Converts a byte array to a hexadecimal string
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}
