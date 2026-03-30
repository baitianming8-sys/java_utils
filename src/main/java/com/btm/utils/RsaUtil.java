package com.btm.utils;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: bai
 * @Date: 2025/11/12 11:04
 * @Description: TODO
 */

public class RsaUtil {
    public static final String KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 512;
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    private static final int MAX_ENCRYPT_BLOCK = 53;
    private static final int MAX_DECRYPT_BLOCK = 64;

    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap();
        keyMap.put("RSAPublicKey", publicKey);
        keyMap.put("RSAPrivateKey", privateKey);
        return keyMap;
    }

    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, privateKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 53) {
            byte[] cache;
            if (inputLen - offSet > 53) {
                cache = cipher.doFinal(data, offSet, 53);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, pubKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 53) {
            byte[] cache;
            if (inputLen - offSet > 53) {
                cache = cipher.doFinal(data, offSet, 53);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, privateKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 64) {
            byte[] cache;
            if (inputLen - offSet > 64) {
                cache = cipher.doFinal(data, offSet, 64);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, pubKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 64) {
            byte[] cache;
            if (inputLen - offSet > 64) {
                cache = cipher.doFinal(data, offSet, 64);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static byte[] getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key)keyMap.get("RSAPrivateKey");
        return key.getEncoded();
    }

    public static byte[] getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key)keyMap.get("RSAPublicKey");
        return key.getEncoded();
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> keyMap = initKey();
        byte[] publicKey = getPublicKey(keyMap);
        byte[] privateKey = getPrivateKey(keyMap);
        Base64Decoder encoder = new Base64Decoder();
        Base64Decoder decoder = new Base64Decoder();
        System.out.println("公钥BASE64：/n" + Base64Encoder.encode(publicKey));
        System.out.println("私钥BASE64：/n" + Base64Encoder.encode(privateKey));
        System.out.println("公钥：/n" + new String(Base64Decoder.decode(Base64Encoder.encode(publicKey))));
        System.out.println("私钥：/n" + new String(privateKey));
        System.out.println("================密钥对构造完毕,甲方将公钥公布给乙方，开始进行加密数据的传输=============");
        String str = "{\"wkUserLevel\":\"0\",\"wkUserBirthday\":\"2018-04-28\",\"createTime\":\"2018-12-20 09:52:55\",\"createTime\":\"2018-12-20 09:52:55\",\"createTime\":\"2018-12-20 09:52:55\",\"createTime\":\"2018-12-20 09:52:55\",\"createTime\":\"2018-12-20 09:52:55\",\"createTime\":\"2018-12-20 09:52:55\"}";
        System.out.println("/n===========甲方向乙方发送加密数据==============");
        System.out.println("原文:" + str);
        byte[] code1 = encryptByPrivateKey(str.getBytes(), privateKey);
        System.out.println("加密后的数据BASE64：" + Base64Encoder.encode(code1));
        System.out.println("加密后的数据：" + new String(code1));
        System.out.println("===========乙方使用甲方提供的公钥对数据进行解密==============");
        byte[] decode1 = decryptByPublicKey(code1, publicKey);
        System.out.println("乙方解密后的数据：" + new String(decode1));
        System.out.println("===========反向进行操作，乙方向甲方发送数据==============/n/n");
        str = "乙方向甲方发送数据RSA算法";
        System.out.println("原文:" + str);
        byte[] code2 = encryptByPublicKey(str.getBytes(), publicKey);
        System.out.println("===========乙方使用公钥对数据进行加密==============");
        System.out.println("加密后的数据BASE64：" + Base64Encoder.encode(code2));
        System.out.println("加密后的数据：" + new String(code2));
        System.out.println("=============乙方将数据传送给甲方======================");
        System.out.println("===========甲方使用私钥对数据进行解密==============");
        byte[] decode2 = decryptByPrivateKey(code2, privateKey);
        System.out.println("甲方解密后的数据：" + new String(decode2));
    }
}
