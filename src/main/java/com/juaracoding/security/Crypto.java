package com.juaracoding.security;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESLightEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

public class Crypto {

//    private static final String defaultKey = "12fc5e5b5abe431b94870823a154e511d2e3419f47c455a9659d89268776a3eb";
//    private static final String defaultKey = "7bf29d8a22a03f43baf953f5c5e4cb264dbf2db3651da21d28daee478a189cce";
    private static final String defaultKey = "2d38e01c52c0d9c475dac174eff7cea6464778ec35f3d78eba5c1421d684a3e6";
    public static String performEncrypt(String keyText, String plainText) {
        try{
            byte[] key = Hex.decode(keyText.getBytes());
            byte[] ptBytes = plainText.getBytes();
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESLightEngine()));
            cipher.init(true, new KeyParameter(key));
            byte[] rv = new byte[cipher.getOutputSize(ptBytes.length)];
            int oLen = cipher.processBytes(ptBytes, 0, ptBytes.length, rv, 0);
            cipher.doFinal(rv, oLen);
            return new String(Hex.encode(rv));
        } catch(Exception e) {
            return "Error";
        }
    }

    public static String performEncrypt(String cryptoText) {
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
        } catch(Exception e) {
            return "Error";
        }
    }

    public static String performDecrypt(String cryptoText) {
        return performDecrypt(defaultKey, cryptoText);
    }

    public static void main(String[] args) {

        Long timeMilis = System.currentTimeMillis();
        System.out.println(timeMilis);
//        String strToEncrypt = "jdbc:mysql://mysqldb:3306/demo?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";//put text to encrypt in here
//        String strToEncrypt = "jdbc:mysql://mysqldb:3306/demo?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";//put text to encrypt in here
        String strToEncrypt = "jdbc:mysql://mysqldb:3306/demo?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";//put text to encrypt in here
        String encryptionResult = new Crypto().performEncrypt(strToEncrypt);
        System.out.println("Encryption Result : "+encryptionResult);

        String strToDecrypt = "c6dbab470f114a103636f9b69f0f3c2d5757b4813aecbf5d64fa27602d15059c43dc278aef9d5c8033133ac99225ac1136808dc28a2b58b8c71711b0dda276714550ecf55794dd1fb6b49a73a87acef4cef9f8bd6b641aa38f004d71bebaa56036aa6d284eca707bdf0e1874bacc7750";//put text to decrypt in here
        String decriptionResult = new Crypto().performDecrypt(strToDecrypt);
        System.out.println("Decryption Result : "+decriptionResult);
    }
}