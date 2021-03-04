package com.keinye.spring.security;


import java.io.IOException;
import java.io.Reader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private boolean postOnly = true;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (this.postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
        AuthReq authReq;
        if ("application/json".equals(request.getHeader("Content-Type"))) {
            authReq = getAuthFromJson(request);
        } else {
            authReq = getAuthFromFormData(request);
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword());
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
	}
	
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
	
    private AuthReq getAuthFromJson(HttpServletRequest request) {
        AuthReq authReq = new AuthReq(SPRING_SECURITY_FORM_USERNAME_KEY, SPRING_SECURITY_FORM_PASSWORD_KEY);

        try (Reader reader = request.getReader()) {
            //authReq = JsonUtil.getObjectMapper().readValue(reader, AuthReq.class);
        } catch (IOException e) {
            throw new AuthenticationServiceException("failed to get username or password from request");
        }

        return authReq;
    }
	
	private AuthReq getAuthFromFormData(HttpServletRequest request) {
		String username = this.obtainUsername(request);
		String password = this.obtainPassword(request);
		
		if (username == null) {
			username = "";
		}
		if (password == null) {
			password = "";
		}
		return new AuthReq(username.trim(), password);
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	private static class AuthReq {
		private String username;
		private String password;
		public AuthReq(String username, String password) {
			this.username = username;
			this.password = password;
		}

	}
}
