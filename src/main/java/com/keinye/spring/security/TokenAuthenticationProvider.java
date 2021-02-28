package com.keinye.spring.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class TokenAuthenticationProvider implements AuthenticationProvider{

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO 自动生成的方法存根
		return false;
	}

}
