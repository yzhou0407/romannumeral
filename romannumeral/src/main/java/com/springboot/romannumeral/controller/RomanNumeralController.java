package com.springboot.romannumeral.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.romannumeral.model.RomanNumeral;
import com.springboot.romannumeral.service.RomanNumeralService;


/**
* The RomanNumeralController class is the RestController
* for RomanNumeralApplication. 
* @author  Yan Zhou
* @version 1.0
* @since   2021-02-15 
*/
@RestController
public class RomanNumeralController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RomanNumeralService romanNumeralService;
	
	
	/**
	* Controller method to create mapping between end point "/romannumeral" and 
	* service RomanNumeralService. 
	*/
	@GetMapping("/romannumeral")
	public ResponseEntity<RomanNumeral> romannumeral(@RequestParam String query) {
		
		log.info(String.format("/romannumeral is getting called with query value:[%s]", query));
		return romanNumeralService.getRomanNumeral(query);

	}
	

}
