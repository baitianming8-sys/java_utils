package com.btm.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class AESUtils {

    // 支持的密钥长度
    public enum KeySize {
        BITS_128(128),
        BITS_192(192),
        BITS_256(256);

        private final int bits;

        KeySize(int bits) {
            this.bits = bits;
        }

        public int getBits() {
            return bits;
        }
    }

    // 生成AES密钥 (Base64编码字符串)
    public static String generateKey(KeySize keySize) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keySize.getBits(), new SecureRandom());
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    // 从Base64字符串恢复密钥
    private static SecretKeySpec parseKey(String base64Key) {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(keyBytes, "AES");
    }

    // AES加密
    public static String encrypt(String plaintext,String base64Key) throws Exception {

        SecretKeySpec secretKey = parseKey(base64Key);

        // 生成随机初始化向量(IV)
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // 初始化加密器
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        // 执行加密
        byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        // 合并IV和密文: IV(16字节) + 密文
        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    // AES解密
    public static String decrypt(String base64Key, String encryptedText) throws Exception {
        SecretKeySpec secretKey = parseKey(base64Key);

        // 解码Base64字符串
        byte[] combined = Base64.getDecoder().decode(encryptedText);

        // 分离IV(前16字节)和实际密文
        byte[] iv = new byte[16];
        byte[] encryptedBytes = new byte[combined.length - 16];
        System.arraycopy(combined, 0, iv, 0, 16);
        System.arraycopy(combined, 16, encryptedBytes, 0, encryptedBytes.length);

        // 初始化解密器
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

        // 执行解密
        byte[] decrypted = cipher.doFinal(encryptedBytes);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        // 示例用法
        // 1. 生成128位密钥
        String secretKey = generateKey(KeySize.BITS_128);
        System.out.println("生成的密钥 (Base64): " + secretKey);

        String originalText = "Hello, AES with key generation!";
        System.out.println("原始文本: " + originalText);

        // 2. 加密
        String encrypted = encrypt(originalText,secretKey);
        System.out.println("加密结果: " + encrypted);

        // 3. 解密
        String decrypted = decrypt(secretKey, encrypted);
        System.out.println("解密结果: " + decrypted);
    }
}