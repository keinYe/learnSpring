package com.keinye.spring.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.keinye.spring.security.TokenService;

@Service
public class TokenServiceImpl implements TokenService{

	@Override
	public UserDetails authenticateToken(String token) {
		// TODO 自动生成的方法存根
		return null;
	}

}
