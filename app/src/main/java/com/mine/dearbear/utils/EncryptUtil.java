package com.mine.dearbear.utils;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {
    private static final String TAG = "EncryptUtil";
    private final static String sAES = "AES";
    private final static String sAesInfo = "AES/CBC/PKCS5Padding";                                  // 算法/模型/补码方式

    //	http body res 加密参数
    private final static byte[] sBodyAesKey = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'};
    private final static byte[] sBodyAesIv = {'0', '1', '0', '2', '0', '3', '0', '4', '0', '5', '0', '6', '0', '7', '0', '8'};
    //  http header res 加密参数
    private final static byte[] sHeaderAesKey = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'};
    private final static byte[] sHeaderAesIv = {'0', '1', '0', '2', '0', '3', '0', '4', '0', '5', '0', '6', '0', '7', '0', '8'};

    /***			zgx 2017/8/8
     * 网络请求Header加密
     * @param text
     * @return
     */
    public static String resEncryptToHeader(String text) {
        return resEncrypt(text, sHeaderAesKey, sHeaderAesIv, "utf-8");
    }

    /***			zgx 2017/8/8
     * 网络请求Body加密
     * @param text
     * @return
     */
    public static String resEncryptToBody(String text) {
        return resEncrypt(text, sBodyAesKey, sBodyAesIv, "utf-8");
    }

    /***			zgx 20173/8/8
     * 网络请求header解密
     * @param decryted
     * @return
     */
    public static String resDecryptFromHeader(String decryted) {
        return resDecrypt(decryted, sHeaderAesKey, sHeaderAesIv, "utf-8");
    }

    /***			zgx 2017/8/8
     * 网络请求Body解密
     * @param decryted
     * @return
     */
    public static String resDecryptFromBody(String decryted) {
        return resDecrypt(decryted, sBodyAesKey, sBodyAesIv, "utf-8");
    }

    /***			zgx	2017/8/8
     * 对text进行	RES + Base64 加密
     * @param text
     * @param key
     * @param iv
     * @return
     */
    public static String resEncrypt(String text, byte[] key, byte[] iv, String encodingFormat) {
        if (TextUtils.isEmpty(text)) {
            return text;
        }
        try {
            Log.d(TAG, "resEncrypt original:" + text);
            byte[] result = encrypt(text, key, iv, encodingFormat);
            Log.d(TAG, "resEncrypt res:" + new String(result, encodingFormat));
            String encrypt = Base64.encodeToString(result, Base64.NO_WRAP);
            Log.d(TAG, "resEncrypt res + base64:" + encrypt);
            return encrypt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 字符串加密	加密方式:	RES/CBC/PKCS5Padding
     * @param text
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(String text, byte[] key, byte[] iv, String encodingFormat) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, sAES);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(sAesInfo);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(text.getBytes(encodingFormat));
        return encrypted;
    }

    /***			zgx	2017/8/8
     * 对encrypted进行	Base64 + RES 解密
     * @param encrypted
     * @param key
     * @param iv
     * @return
     */
    public static String resDecrypt(String encrypted, byte[] key, byte[] iv, String encodingFormat) {
        if (TextUtils.isEmpty(encrypted)) {
            return encrypted;
        }
        try {
            byte[] enc = Base64.decode(encrypted, Base64.NO_WRAP);
            Log.d(TAG, "resDecrypted res:" + new String(enc, encodingFormat));
            byte[] result = decrypt(enc, key, iv);
            String originalString = new String(result, encodingFormat);
            Log.d(TAG, "resDecrypted original:" + originalString);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***			zgx 2017/8/8
     * 字符串解密	解密方式:	RES/CBC/PKCS5Padding
     * @param encrypted
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] encrypted, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, sAES);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(sAesInfo);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        return cipher.doFinal(encrypted);
    }

    /***
     * 字符串MD5加密
     * @param msg
     * @return
     */
    public static String md5(String msg) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(msg.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String fillMD5(String md5) {
        return md5.length() == 32 ? md5 : fillMD5("0" + md5);
    }
}
