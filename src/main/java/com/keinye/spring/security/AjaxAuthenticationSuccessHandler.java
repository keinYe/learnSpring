package com.keinye.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.keinye.spring.common.struct.Response;
import com.keinye.spring.common.utils.JsonUtil;

public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		CSRFUtil.writeCSRFToHeader(request, response);
		CSRFUtil.writeCSRFToCookie(request, response);
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(JsonUtil.toJson(Response.ok()));
		response.getWriter().flush();
		
		clearAuthenticationAttributes(request);
	}
}
