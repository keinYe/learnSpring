package com.keinye.spring.properties;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.keinye.spring.common.utils.KeyUtils;

import lombok.Getter;
import lombok.Setter;


@Component
@ConfigurationProperties(prefix = "rsa.key")
@Setter
@Getter
public class KeyProperties {
	private String pubKeyFile;
	private String priKeyFile;
	
	private PublicKey publicKey;
	private PrivateKey privateKey;
	
	private static final Logger logger = LoggerFactory.getLogger(KeyProperties.class);
	
	@PostConstruct
	public void createRsaKey() throws Exception {
		String classpath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
		Path priKeyPath = Path.of(classpath, priKeyFile);
		Path pubKeyPath = Path.of(classpath, pubKeyFile);
		if (Files.notExists(priKeyPath) || Files.notExists(pubKeyPath)) {
			logger.info("Create RSA KEY");
			KeyUtils.generateKey(pubKeyPath.toString(), priKeyPath.toString(), "Hello", 0);
		}
		
		this.privateKey = KeyUtils.getPrivateKey(priKeyPath.toString());
		this.publicKey = KeyUtils.getPublicKey(pubKeyPath.toString());
	}
}
