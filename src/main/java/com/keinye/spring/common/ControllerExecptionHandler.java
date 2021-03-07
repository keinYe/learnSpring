package com.keinye.spring.common;

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
	
	@ExceptionHandler(ResultException.class)
	protected ResponseEntity<Response> handleDomainException(
			ResultException ex,
			WebRequest request) {
		return ResponseEntity.ok().body(Response.of(ex));
	}
}
