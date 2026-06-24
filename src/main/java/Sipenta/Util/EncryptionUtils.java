package Sipenta.Util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    private static SecretKeySpec getKeySpec() {
        String key = System.getProperty("KEY");

        if (key == null || key.isEmpty()) {
            throw new RuntimeException("KEY belum diatur. Isi VM Options dengan: -DKEY=\"SIPENTA2026KEY01\"");
        }

        // Menghapus tanda petik jika ikut terbaca oleh Java
        key = key.replace("\"", "").trim();

        if (key.length() != 16) {
            throw new RuntimeException(
                "KEY harus 16 karakter. KEY sekarang: " + key + 
                " | jumlah karakter: " + key.length()
            );
        }

        byte[] secretKey = key.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(secretKey, ALGORITHM);
    }

    public static String encrypt(String value) {
        try {
            if (value == null) {
                return null;
            }

            SecretKeySpec spec = getKeySpec();

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, spec);

            byte[] encryptedBytes = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (Exception e) {
            System.err.println("Error saat enkripsi: " + e.getMessage());
            return null;
        }
    }

    public static String decrypt(String encryptedValue) {
        try {
            if (encryptedValue == null) {
                return null;
            }

            SecretKeySpec spec = getKeySpec();

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, spec);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedValue);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);

            return new String(decryptedBytes, StandardCharsets.UTF_8);

        } catch (Exception e) {
            System.err.println("Error saat dekripsi: " + e.getMessage());
            return null;
        }
    }
}