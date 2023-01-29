package org.ahmed.init.utility;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.ahmed.init.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
	
	@Autowired
	Environment environment;
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> exceptionHandler(Exception exception){
		ErrorInfo error = new ErrorInfo();
		error.setErrorMessage(environment.getProperty("General.EXCEPTION_MESSAGE"));
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setTimestampInfo(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(error, HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorInfo> resourceNotFoundExceptionHandler(ResourceNotFoundException resourceNotFoundException){
		ErrorInfo error = new ErrorInfo();
		error.setErrorMessage(resourceNotFoundException.getMessage());
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		error.setTimestampInfo(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(error, HttpStatus.NOT_FOUND);	
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorInfo> exceptionHandler(MethodArgumentNotValidException exception){
		ErrorInfo error = new ErrorInfo();
		String errorMsg = exception.getBindingResult().getAllErrors().stream().map(x->x.getDefaultMessage())
				.collect(Collectors.joining(", "));
		error.setErrorMessage(errorMsg);
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setTimestampInfo(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(error, HttpStatus.BAD_REQUEST);	
	}
}
