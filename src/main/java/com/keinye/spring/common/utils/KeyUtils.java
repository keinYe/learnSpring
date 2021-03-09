package com.keinye.spring.common.utils;

import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtils {
	private static final int DEFAULT_KEY_SIZE = 2048;
	
	public static PublicKey getPublicKey() throws Exception {
		byte[] bytes = Base64.getDecoder().decode("hello".getBytes());
		KeySpec spec = new X509EncodedKeySpec(bytes);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		return factory.generatePublic(spec);
	}
	
	public static PrivateKey getPrivateKey() throws Exception {
		byte[] bytes = Base64.getDecoder().decode("hello".getBytes());
		KeySpec spec = new PKCS8EncodedKeySpec(bytes);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		return factory.generatePrivate(spec);
	}
}
