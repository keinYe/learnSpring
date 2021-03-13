package com.keinye.spring.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.keinye.spring.common.struct.Response;
import com.keinye.spring.common.struct.ResultException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ControllerExecptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(ControllerExecptionHandler.class);
	
	@ExceptionHandler(ResultException.class)
	protected ResponseEntity<Response> handleDomainException(
			ResultException ex,
			WebRequest request) {
		logger.info(ex.toString());
		return ResponseEntity.ok().body(Response.of(ex));
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ex.printStackTrace();
		// TODO 自动生成的方法存根
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
}
