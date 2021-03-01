package com.keinye.spring.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {
	UserDetails authenticateToken(String token);
}
