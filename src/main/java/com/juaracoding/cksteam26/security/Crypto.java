package com.juaracoding.cksteam26.security;

import java.util.Scanner;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESLightEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

public class Crypto {

    private static String defaultKey = System.getenv("ENCRYPTION_KEY");

    public Crypto() {
        // empty constructor
    }

    public Crypto(String key) {
        defaultKey = key;
    }

    public static String performEncrypt(String keyText, String plainText) {
        try {
            byte[] key = Hex.decode(keyText.getBytes());
            byte[] ptBytes = plainText.getBytes();
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESLightEngine()));
            cipher.init(true, new KeyParameter(key));
            byte[] rv = new byte[cipher.getOutputSize(ptBytes.length)];
            int oLen = cipher.processBytes(ptBytes, 0, ptBytes.length, rv, 0);
            cipher.doFinal(rv, oLen);
            return new String(Hex.encode(rv));
        } catch (Exception e) {
            return "Error";
        }
    }

    public static String performEncrypt(String cryptoText) {
        if (defaultKey == null)
            throw new IllegalStateException("ENCRYPTION_KEY environment variable not set");
        return performEncrypt(defaultKey, cryptoText);
    }

    public static String performDecrypt(String keyText, String cryptoText) {
        try {
            byte[] key = Hex.decode(keyText.getBytes());
            byte[] cipherText = Hex.decode(cryptoText.getBytes());
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESLightEngine()));
            cipher.init(false, new KeyParameter(key));
            byte[] rv = new byte[cipher.getOutputSize(cipherText.length)];
            int oLen = cipher.processBytes(cipherText, 0, cipherText.length, rv, 0);
            cipher.doFinal(rv, oLen);
            return new String(rv).trim();
        } catch (Exception e) {
            return "Error";
        }
    }

    public static String performDecrypt(String cryptoText) {
        if (defaultKey == null)
            throw new IllegalStateException("ENCRYPTION_KEY environment variable not set");
        return performDecrypt(defaultKey, cryptoText);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String keyFromEnv = System.getenv("ENCRYPTION_KEY");
        if (keyFromEnv == null || !keyFromEnv.matches("^[a-fA-F0-9]{64}$")) {
            System.out.println(
                    "Invalid or missing ENCRYPTION_KEY environment variable. Please set a 64-character hex key.");
            scanner.close();
            return;
        }

        System.out.println("Using ENCRYPTION_KEY from environment.");
        System.out.println("Choose operation: (1) Encrypt (2) Decrypt");
        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            System.out.println("Enter text to encrypt:");
            String plaintext = scanner.nextLine();
            String encrypted = performEncrypt(keyFromEnv, plaintext);
            System.out.println("Encrypted: " + encrypted);
        } else if ("2".equals(choice)) {
            System.out.println("Enter text to decrypt:");
            String encryptedText = scanner.nextLine();
            String decrypted = performDecrypt(keyFromEnv, encryptedText);
            System.out.println("Decrypted: " + decrypted);
        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}
