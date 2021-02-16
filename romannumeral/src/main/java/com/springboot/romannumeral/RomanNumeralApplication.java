package com.springboot.romannumeral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* RomanNumeralApplication
* Description: This is the web application which provides a 
* web service that takes in parameter query's value and convert
* it into a Roman numeral representation if the queried value is
* valid.
* End point: /romannumeral
* @author  Yan Zhou
* @version 1.0
* @since   2021-02-15 
*/
@SpringBootApplication
public class RomanNumeralApplication {

	public static void main(String[] args) {
		SpringApplication.run(RomanNumeralApplication.class, args);
	}

}
