package com.keinye.spring.properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;


@Component
@ConfigurationProperties(prefix = "key")
@Setter
@Getter
public class KeyProperties {
	//@Value("${rsa.key.pubKeyFile}")
	private String pubKeyFile;
	private String priKeyFile;
	
	private static final Logger logger = LoggerFactory.getLogger(KeyProperties.class);
	
	@PostConstruct
	public void createRsaKey() {
		logger.info(priKeyFile);
		logger.info(pubKeyFile);
	}
}
