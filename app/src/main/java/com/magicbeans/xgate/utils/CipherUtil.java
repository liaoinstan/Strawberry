package com.magicbeans.xgate.utils;

import android.util.Base64;
import android.util.Log;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtil {
    public static final String TAG = "CipherUtil";
    private static CipherUtil instance;

    public static CipherUtil getInstance() {
        if (instance == null) {
            instance = new CipherUtil();
        }
        return instance;
    }

    private static final String CipherMode = "AES/ECB/PKCS7Padding";
    private static final String COMMON_KEY = "SBNCB2013";

    public String testEncrypt(String content, String key) {
        return aesEncryptedAsBase64(content.getBytes(), md5Bytes(key));
    }

    public String encryptRefreshTokenData(String data) {
        //String data = accountId + "|" + deviceId + "|" + deviceType + "|" + time + "|" + token;
        String encryptedData = aesEncryptedAsBase64((data).getBytes(), md5Bytes(COMMON_KEY));
        String urlEncodedData = URLEncoder.encode(encryptedData);
        return urlEncodedData;
    }

    public String encryptPassword(String password) {
        Log.d(TAG, TAG + " original password: " + password);
        String randomKey = getRandomKey();
        Log.d(TAG, TAG + " randomKey: " + randomKey);
        String firstEncryptedData = aesEncryptedAsBase64(password.getBytes(), md5Bytes(randomKey));
        Log.d(TAG, TAG + " firstEncryptedData: " + firstEncryptedData);
        String secondEncryptedData = aesEncryptedAsBase64((randomKey + firstEncryptedData).getBytes(),
                md5Bytes(COMMON_KEY));
        Log.d(TAG, TAG + " secondEncryptedData: " + secondEncryptedData);
        String urlEncodedData = URLEncoder.encode(secondEncryptedData);
        Log.d(TAG, TAG + " urlEncodedData: " + urlEncodedData);
        return urlEncodedData;
    }

    private String aesEncryptedAsBase64(byte[] content, byte[] key) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] result = cipher.doFinal(content);
            return Base64.encodeToString(result, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getRandomKey() {
        int min = 10000000;
        int max = 99999999;
        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        return String.valueOf(i1);
    }

    private byte[] md5Bytes(final String s) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}