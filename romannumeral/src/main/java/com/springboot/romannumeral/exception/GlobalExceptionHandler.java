package com.springboot.romannumeral.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
* The GlobalExceptionHandler is the customized exception handler 
* for RomanNumeralApplication. 
* @author  Yan Zhou
* @version 1.0
* @since   2021-02-15 
*/
@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	/**
	*  noInputHandling is used to handle NoInputException thrown from 
	* RomanNumeralService.getRomanNumeral method
	* @param exception: type NoInputException
	* @param request: 	type WebRequest
	*/
	@ExceptionHandler(NoInputException.class)
	public ResponseEntity<?> noInputHandling(NoInputException exception, WebRequest request){
		ErrorDetails errorDetails = 
				new ErrorDetails(new Date(), 1, exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	/**
	* invalidIntegerFormatHandling is used to handle NoInputException thrown from 
	* RomanNumeralService.getRomanNumeral method.
	* @param exception: type InvalidIntegerFormatException
	* @param request: 	type WebRequest
	*/
	@ExceptionHandler(InvalidIntegerFormatException.class)
	public ResponseEntity<?> invalidIntegerFormatHandling(InvalidIntegerFormatException exception, WebRequest request){
		ErrorDetails errorDetails = 
				new ErrorDetails(new Date(), 2, exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	/**
	* invalidIntegerFormatHandling is used to handle InputIntegerOutOfRangException thrown from 
	* RomanNumeralService.getRomanNumeral method.
	* @param exception: type InvalidIntegerFormatException
	* @param request: 	type WebRequest
	*/
	@ExceptionHandler(InputIntegerOutOfRangException.class)
	public ResponseEntity<?> invalidIntegerFormatHandling(InputIntegerOutOfRangException exception, WebRequest request){
		ErrorDetails errorDetails = 
				new ErrorDetails(new Date(), 3, exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	/**
	* globalExceptionHandling is used to handle Exception thrown from 
	* RomanNumeralService.getRomanNumeral method.
	* @param exception: type Exception
	* @param request: 	type WebRequest
	*/
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request){
		ErrorDetails errorDetails = 
				new ErrorDetails(new Date(), 0, exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
