package com.ssafy.home.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.home.common.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {

    // 커스텀 예외 처리
	@ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException ex) {
		ex.printStackTrace();
    	ErrorCode errorCode = ex.getErrorCode();
    	log.warn("Handled CustomException: {}, Code: {}", errorCode.getMessage(), errorCode.name(), ex);
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode.getStatus().value(), errorCode.getMessage()));
    }

    // 그 외 모든 예외
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
		ex.printStackTrace();
    	log.error("Unexpected error occurred", ex);
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiResponse.fail(
                		ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value(), 
                		ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }

}