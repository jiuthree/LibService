package com.example.library.pro.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;

/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {





	@ExceptionHandler(value = { RequestException.class })
	public ResponseEntity<ExceptionResponse> handleOntologyException(RequestException e) {
		ExceptionResponse response = new ExceptionResponse(e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}







}
