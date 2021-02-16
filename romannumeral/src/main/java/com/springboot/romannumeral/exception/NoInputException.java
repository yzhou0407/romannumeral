package com.springboot.romannumeral.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
* The InputIntegerOutOfRangException is the customized exception
* for RomanNumeralApplication. 
* UseCase:
* 	1. When get request parameter query has empty value.
*   2. When get request parameter query only contains white spaces.
* When this exception is caught, response status will be HttpStatus.BAD_REQUEST
* @author  Yan Zhou
* @version 1.0
* @since   2021-02-15 
*/
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoInputException extends RuntimeException {


	private static final long serialVersionUID = -8063118332070871531L;

	/**
	* The constructor of NoInputException class.
	* @param message : Description of the exception.
	*/
	public NoInputException(String message) {
		super(message);
	}
}
