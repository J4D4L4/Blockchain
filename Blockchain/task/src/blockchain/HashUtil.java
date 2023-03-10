package blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class HashUtil {

    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte elem : hash) {
                String hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    public static int startsWithXZero(String string) {
        int length = string.length() - string.replaceAll("^0+", "").length();
        return length;
    }

    public static PublicPrivateKeys createKeys() throws NoSuchAlgorithmException, NoSuchProviderException {
        PublicPrivateKeys keys = new PublicPrivateKeys(1024);
        keys.createKeys();
        return keys;

    }
}
