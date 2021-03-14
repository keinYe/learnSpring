package com.keinye.spring.common.struct;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


@Getter
public class ResultException extends RuntimeException{

	private static final long serialVersionUID = -2589167582537278060L;
	
	private int errorCode;
	private String description;
	private Object errorData;	
	
	private ResultException(int errorCode, @NonNull String message, String description) {
		super(message);
		this.errorCode = errorCode;
		this.description = description;
	}
	
	private ResultException(int errorCode, @NonNull String message, String description,
			Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.description = description;
	}
	
	public static ResultException of(int errorCode, @NonNull String message) {
		return new ResultException(errorCode, message, null);
	}
	
	public static ResultException of(@NonNull ErrorCode errorCode) {
		return new ResultException(
				errorCode.getErrorCode(), 
				errorCode.getErrorMessage(),
				errorCode.getDescription());
	}
	
	public ResultException description(String description) {
		this.description = description;
		return this;
	}
	
	public ResultException errorData(Object errorData) {
		this.errorData = errorData;
		return this;
	}
	
	public ResultException cause(Throwable cause) {
		return new ResultException(
				this.getErrorCode(), 
				this.getMessage(), 
				this.getMessage(), 
				cause);
	}
}
