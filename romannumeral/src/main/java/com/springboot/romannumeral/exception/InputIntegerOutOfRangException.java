package com.springboot.romannumeral.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
* The InputIntegerOutOfRangException is the customized exception
* for RomanNumeralApplication. 
* UseCase:
* 	When get request parameter query has integer value that is 
* 	out of supported range[1,3999].
* When this exception is caught, response status will be HttpStatus.UNPROCESSABLE_ENTITY
* @author  Yan Zhou
* @version 1.0
* @since   2021-02-15 
*/
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InputIntegerOutOfRangException extends RuntimeException {

	private static final long serialVersionUID = 2528726416574738851L;

	/**
	* The constructor of InputIntegerOutOfRangException class.
	* @param message : Description of the exception.
	*/	
	public InputIntegerOutOfRangException(String message) {
		super(message);
	}

}
