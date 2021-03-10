package com.keinye.spring.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.springframework.util.ResourceUtils;

public class KeyUtils {
	private static final int DEFAULT_KEY_SIZE = 2048;
	
	public static PublicKey getPublicKey(String fileName) throws Exception {
		byte[] bytes = readFile(fileName);
		return getPublicKey(bytes);
	}
	public static PublicKey getPublicKey(byte[] bytes) throws Exception {
		KeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(bytes));
		KeyFactory factory = KeyFactory.getInstance("RSA");
		return factory.generatePublic(spec);
	}
	
	public static PrivateKey getPrivateKey(String fileName) throws Exception {
		byte[] bytes = readFile(fileName);
		return getPrivateKey(bytes);
	}
	
	public static PrivateKey getPrivateKey(byte[] bytes) throws Exception {
		KeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(bytes));
		KeyFactory factory = KeyFactory.getInstance("RSA");
		return factory.generatePrivate(spec);
	}
	
    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret, int keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(Math.max(keySize, DEFAULT_KEY_SIZE), secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        // 获取公钥并写出
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        publicKeyBytes = Base64.getEncoder().encode(publicKeyBytes);
        writeFile(publicKeyFilename, publicKeyBytes);
        // 获取私钥并写出
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        privateKeyBytes = Base64.getEncoder().encode(privateKeyBytes);
        writeFile(privateKeyFilename, privateKeyBytes);
    }
	
	private static byte[] readFile(String fileName) throws IOException {
		return Files.readAllBytes(new File(fileName).toPath());
	}
	
	private static void writeFile(String path, byte[] bytes) throws IOException{
        File dest = new File(path);
        if (!dest.exists()) {
            dest.createNewFile();
        }
        Files.write(dest.toPath(), bytes);
	}
}
