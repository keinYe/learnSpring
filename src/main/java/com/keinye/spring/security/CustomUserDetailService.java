package com.keinye.spring.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.keinye.spring.common.utils.PasswordUtil;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username != null) {
			return User.builder()
					.username(username)
					.password(PasswordUtil.encoder(username))
					.build();
		}
		throw new UsernameNotFoundException(username);
	}

}
