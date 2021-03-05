package com.keinye.spring.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.NonNull;

public class PasswordUtil {
	public static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	
	public static String encoder(@NonNull String password) {
		return ENCODER.encode(password);
	}
	
	public static boolean matches(@NonNull String rawPassword, @NonNull String encoderPassword) {
		return ENCODER.matches(rawPassword, encoderPassword);
	}
	
	public static PasswordEncoder getEncoder() {
		return ENCODER;
	}
}
