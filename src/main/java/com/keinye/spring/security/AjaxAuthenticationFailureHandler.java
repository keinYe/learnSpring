package com.keinye.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.keinye.spring.common.JsonUtil;
import com.keinye.spring.common.MyError;
import com.keinye.spring.common.struct.Response;

public class AjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		Response error;
		
		if (exception instanceof DisabledException) {
			error = Response.error(MyError.LOGIN_ACCOUNT_DISABLED);
			response.addHeader("X-inactive-username", "true");
		} else if (exception instanceof BadCredentialsException) {
			error = Response.error(MyError.LOGIN_BAD_CREDENTIAL);
			response.addHeader("X-invalid-username-password", "true");
		} else {
			error = Response.error(MyError.LOGIN_ERROR);
		}
		
		response.getWriter().println(JsonUtil.toJson(error.description(exception.getMessage())));
	}
}
