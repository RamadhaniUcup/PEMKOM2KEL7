package Sipenta.Util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {

    // Konstanta pilihan algoritma SHA
    public static final String SHA_1 = "SHA-1";
    public static final String SHA_224 = "SHA-224";
    public static final String SHA_256 = "SHA-256";
    public static final String SHA_384 = "SHA-384";
    public static final String SHA_512 = "SHA-512";

    /**
     * Method untuk menghasilkan nilai hash dari sebuah teks.
     *
     * @param input Teks mentah, misalnya password atau RFID
     * @param algorithm Pilihan algoritma, misalnya SHA-256
     * @return String hasil hash dalam format hexadecimal
     */
    public static String getHash(String input, String algorithm) {
        try {
            if (input == null) {
                return null;
            }

            MessageDigest md = MessageDigest.getInstance(algorithm);

            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Kesalahan: Algoritma " + algorithm + " tidak didukung. " + e.getMessage());
            return null;
        }
    }
}