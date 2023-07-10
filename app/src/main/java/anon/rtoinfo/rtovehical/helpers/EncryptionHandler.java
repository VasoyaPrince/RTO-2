package anon.rtoinfo.rtovehical.helpers;

import android.util.Base64;


import anon.rtoinfo.rtovehical.utils.GlobalReferenceEngine;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class EncryptionHandler {
    private static String generateMD5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & -1);
                while (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static String encrypt(String str) {
        return encrypt(str, GlobalReferenceEngine.dataAccessKey);
    }

    public static String encrypt(String str, String str2) {
        byte[] bArr = new byte[0];
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
            Cipher instance = Cipher.getInstance("AES/ECB/PKCS5Padding");
            instance.init(1, secretKeySpec);
            bArr = instance.doFinal(str.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(bArr, 2);
    }

    public static String decrypt(String str, String str2) {
        byte[] bArr = new byte[0];
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "Flash");
            Cipher instance = Cipher.getInstance("AES/ECB/PKCS5Padding");
            instance.init(2, secretKeySpec);
            bArr = instance.doFinal(Base64.decode(str, 2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(bArr);
    }
}
