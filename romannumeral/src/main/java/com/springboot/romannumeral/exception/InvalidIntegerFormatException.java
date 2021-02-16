package com.springboot.romannumeral.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


/**
* The InvalidIntegerFormatException is the customized exception
* for RomanNumeralApplication. 
* UseCase:
* 	When get request parameter query's value is not in valid integer
*   format.
* When this exception is caught, response status will HttpStatus.UNPROCESSABLE_ENTITY
* @author  Yan Zhou
* @version 1.0
* @since   2021-02-15 
*/
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidIntegerFormatException extends RuntimeException {

	private static final long serialVersionUID = -8880561666979654424L;
	
	/**
	* The constructor of InvalidIntegerFormatException class.
	* @param message : Description of the exception.
	*/
	public InvalidIntegerFormatException(String message) {
		super(message);
	}

}
