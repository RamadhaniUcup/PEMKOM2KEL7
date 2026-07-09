package Sipenta.Util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    // Kunci 16 karakter yang sudah tertanam (Hardcoded)
    private static final String SECRET_KEY = "SIPENTA2026KEY01"; 

    private static SecretKeySpec getKeySpec() {
        byte[] secretKey = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(secretKey, ALGORITHM);
    }

    public static String encrypt(String value) {
        try {
            if (value == null || value.isEmpty()) return null;
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, getKeySpec());
            byte[] encryptedBytes = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            System.err.println("Error enkripsi: " + e.getMessage());
            return null;
        }
    }

    public static String decrypt(String encryptedValue) {
        try {
            if (encryptedValue == null || encryptedValue.isEmpty()) return null;
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, getKeySpec());
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedValue);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.err.println("Error dekripsi: " + e.getMessage());
            return null;
        }
    }
}