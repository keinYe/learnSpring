package com.keinye.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.keinye.spring.common.struct.Response;
import com.keinye.spring.common.utils.JsonUtil;

public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler{

	@Override
	public void onLogoutSuccess(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Authentication authentication) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(JsonUtil.toJson(Response.ok()));
		response.getWriter().flush();
	}

}
