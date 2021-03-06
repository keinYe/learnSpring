package com.keinye.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.keinye.spring.common.JsonUtil;

public class AjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		Response error;
		
		
		response.getWriter().println(JsonUtil.toJson(error.description(exception.getMessage())));
	}
}
