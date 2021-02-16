package com.springboot.romannumeral.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.romannumeral.exception.InputIntegerOutOfRangException;
import com.springboot.romannumeral.exception.InvalidIntegerFormatException;
import com.springboot.romannumeral.exception.NoInputException;
import com.springboot.romannumeral.model.RomanNumeral;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
* RomanNumeralService contains logics of the service.
* @author  Yan Zhou
* @version 1.0
* @since   2021-02-15 
*/
@Service
@Component
public class RomanNumeralService {

	// Logger instance for the service to write logs into console and log file.
	private Logger log = LoggerFactory.getLogger(RomanNumeralService.class);
	
	// Min decimal integer value being supported by the service.
	private int rangeMin = 1;
	
	// Max decimal integer value being supported by the service.
	private int rangeMax = 3999;
	
	// MeterRegistry to be used customized metrics.
	@SuppressWarnings("unused")
	private MeterRegistry meterRegistry;
	
	// Counter for total number of requests to this service.
	private Counter totalCounter;
	// Counter for total number of requests(which result in error) to this service.
	private Counter errorCounter;
	
	/**
	* Constructor for RomanNumeralService.
	* @param meterRegistry is passed in from the caller.
	*/
	public RomanNumeralService(MeterRegistry meterRegistry) {
		
		this.meterRegistry = meterRegistry;
		this.totalCounter = Counter.builder("romanNumeralService.total.request")
				.description("The number of total request to RomanNumeralService")
				.register(meterRegistry);
		this.errorCounter = Counter.builder("romanNumeralService.error.request")
				.description("The number of error request to RomanNumeralService")
				.register(meterRegistry);
	}
	
	/**
	* getRomanNumeral returns ResponseEntity in which a RomanNumeral
	* object is wrapped, along with HttpStatus.OK.
	* @param query: value of request parameter query
	*/
	public ResponseEntity<RomanNumeral> getRomanNumeral(@RequestParam String query) {
		
		log.trace(String.format("getRomanNumeral starts: query:[%s]", query));
		
		// totalCounter will be incremented whenever a request to '/romannumeral' endpoint is received.
		this.totalCounter.increment();
		
		int intValue;
		
		// Step1: check if query is Null or only contains white spaces.
		if (isNullOrEmpty(query)) {

			log.error("Request URI parameter query has either null value or only white spaces.");
			this.errorCounter.increment();
			throw new NoInputException(generateErrorMessage(query, 1, this.rangeMin, this.rangeMax));
		}

		// Step2: try to convert the query value into a integer.
		try {
			intValue = convertStringToInteger(query);
			
		} catch (Exception ex) {
			
			log.error("Request URI parameter query has value with invalid integer format.");
			this.errorCounter.increment();
			throw new InvalidIntegerFormatException(generateErrorMessage(query, 2, this.rangeMin, this.rangeMax));
		}

		// Step3: check if the integer is within the supported range.
		if (!isInSupportedRange(intValue, this.rangeMin, this.rangeMax)) {
			
			log.error("Request URI parameter query has value out of supported range.");
			this.errorCounter.increment();
			throw new InputIntegerOutOfRangException(generateErrorMessage(query, 3, this.rangeMin, this.rangeMax));
		}

		// Step4: When all validations(step1 to step2) passed, try to get the Roman numeral representation of the integer.
		String romanNumeral = intToRoman(intValue);
		
		log.info(String.format("Roman numeral of queried integer %s is:[%s]", query, romanNumeral));
		log.trace(String.format("getRomanNumeral ends: query:[%s]", query));
		
		return new ResponseEntity<>(new RomanNumeral(query, romanNumeral), HttpStatus.OK);

	}
	
	/**
	* intToRoman returns a String value represents the Roman numeral of n.
	* @param n : decimal integer to be converted to Roman numeral.
	*/
	private String intToRoman(int n) {
		
		log.trace(String.format("intToRoman starts: n:[%d]", n));
		
		// The String arrays below are created by referencing Wikipedia's standard form.
		// https://en.wikipedia.org/wiki/Roman_numerals
		String M[] = { "", "M", "MM", "MMM" };
		String C[] = { "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" };
		String X[] = { "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" };
		String I[] = { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" };
		
		String result = M[n / 1000] + C[(n / 100) % 10] + X[(n / 10) % 10] + I[n % 10];
		
		log.trace(String.format("intToRoman ends: result:[%s]", result));
		
		return result;
	}

	/**
	* isNullOrEmpty checks if request parameter query is null or only contains white spaces.
	* @param query : request parameter query's value.
	*/
	private boolean isNullOrEmpty(String query) {
		
		log.trace(String.format("isNullOrEmpty starts: query:[%s]", query));
		
		boolean result = (query == null || query.trim().isEmpty());
		
		log.trace(String.format("isNullOrEmpty ends: result:[%b]", result));
		
		return result;
	}
	

	/**
	* isNullOrEmpty returns integer value of request parameter query.
	* @param query : request parameter query's value.
	*/
	private int convertStringToInteger(String query) throws NumberFormatException{
		
		log.trace(String.format("convertStringToInteger starts: query:[%s]", query));
			
		int result;
		try {
			
			result = Integer.parseInt(query);
			
		} catch (NumberFormatException ex) {
			
			log.error(String.format("NumberFormatException happens when parse query value[%s].", query));
			throw ex;
			
		}
		
		log.trace(String.format("isNullOrEmpty ends: result:[%d]", result));
		return result;
	}
	
	/**
	* isInSupportedRange checks if the integer value is within supported range.
	* @param integer : value to check.
	* @param min: supported min value.
	* @param max: supported max value.
	*/
	private boolean isInSupportedRange(int integer, int min, int max) {
		
		log.trace(String.format("isInSupportedRange starts: integer:[%d], min:[%d], max[%d]", integer, min, max));
		
		boolean result  = false;
		
		if (integer >= min && integer <= max) {
			log.trace(String.format("integer [%d] is within supported range[%d, %d].", integer, min, max));
			result = true;
		} else {
			log.trace(String.format("integer [%d] is out of supported range[%d, %d].", integer, min, max));
		}
		
		log.trace(String.format("isInSupportedRange ends: result:[%b]", result));
		return result;
	}
	
	/**
	* generateErrorMessage generates a description of the error happens during the validation against
	* request parameter query.
	* @param query: request parameter query's value
	* @param errorCode : integer value represents which type of the error it is.
	* @param min: supported min value.
	* @param max: supported max value.
	*/
	private String generateErrorMessage(String query, int errorCode, int min, int max) {
		
		log.trace(String.format("generateErrorMessage starts: query:[%s], errorCode:[%d], min[%d], max[%d]", query, errorCode, min, max));

		String errorMessage = null;
		
		switch (errorCode) {
			case 1 :
				// query value is null or only contains white spaces.
				errorMessage = String.format(
						"Input query value [%s] is missing or only contains whitespace.",
						query);
				break;
			case 2:
				// query value is not a valid integer format.
				errorMessage = String.format(
						"Input query value [%s] does not have correct integer format.",
						query);
				break;
			case 3:
				// query value is out of supported range.
				errorMessage = String.format(
						"Input query value [%s] is out of supported range.",
						query);
				break;
			default:
				errorMessage = String.format(
						"Input query value [%s] cannot be parsed into integer value.",
						query);
		
		}
		
		errorMessage = errorMessage +
						 String.format(
								" Please give an integer value between %d and %d. Example: /romannumeral?query=123",
								min, max);
		
		log.trace(String.format("generateErrorMessage ends: errorMessage:[%s]", errorMessage));		
		return errorMessage;
	}
}
