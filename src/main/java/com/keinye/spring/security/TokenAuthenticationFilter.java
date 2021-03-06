package com.keinye.spring.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context.getAuthentication() != null && context.getAuthentication().isAuthenticated()) {
			
		} else {
			Map<String, String[]> params = request.getParameterMap();
			if (!params.isEmpty() && params.containsKey("token")) {
				String token = params.get("token")[0];
				if (token != null) {
					Authentication auth = new TokenAuthentication(token);
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
			request.setAttribute("com.keinye.spring.security.TokenAuthenticationFilter.FILTERED", true);
		}
		filterChain.doFilter(request, response);
	}
	
	
	class TokenAuthentication implements Authentication {
		private static final long serialVersionUID = 1L;
		private String token;
		
		public TokenAuthentication(String token) {
			this.token = token;
		}

		@Override
		public String getName() {
			// TODO 自动生成的方法存根
			return token;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			// TODO 自动生成的方法存根
			return null;
		}

		@Override
		public Object getCredentials() {
			// TODO 自动生成的方法存根
			return token;
		}

		@Override
		public Object getDetails() {
			// TODO 自动生成的方法存根
			return null;
		}

		@Override
		public Object getPrincipal() {
			// TODO 自动生成的方法存根
			return null;
		}

		@Override
		public boolean isAuthenticated() {
			// TODO 自动生成的方法存根
			return false;
		}

		@Override
		public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
			// TODO 自动生成的方法存根
			
		}
		
	}

}
