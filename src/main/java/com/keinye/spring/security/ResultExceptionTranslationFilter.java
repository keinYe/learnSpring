package com.keinye.spring.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import com.keinye.spring.common.struct.Response;
import com.keinye.spring.common.struct.ResultException;
import com.keinye.spring.common.utils.JsonUtil;

public class ResultExceptionTranslationFilter extends GenericFilterBean{
	private static final Logger logger = LoggerFactory.getLogger(ResultExceptionTranslationFilter.class);
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info(getFilterName());
        try {
            chain.doFilter(request, response);
        } catch (ResultException ex) {
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(JsonUtil.toJson(Response.of(ex)));
            response.getWriter().flush();
        }
	}

}
