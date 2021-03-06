package com.keinye.spring.common.struct;

public interface ErrorCode {

	int getErrorCode();
	
	String getErrorMessage();
	
	default String getDescription() {
		return null;
	}
}
