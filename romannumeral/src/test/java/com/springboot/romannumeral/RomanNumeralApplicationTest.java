package com.springboot.romannumeral;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.springboot.romannumeral.exception.ErrorDetails;
import com.springboot.romannumeral.model.RomanNumeral;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RomanNumeralApplicationTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void validLargeQueryValue() throws Exception {
		
		ResponseEntity<RomanNumeral> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/romannumeral?query=3999", RomanNumeral.class);
		RomanNumeral expectedResult = new RomanNumeral("3999","MMMCMXCIX");
		RomanNumeral actualResult = responseEntity.getBody();
		
		Assertions.assertEquals(expectedResult.toString(),actualResult.toString());
		
	}
	
	@Test
	public void validSmallQueryValue() throws Exception {
		
		ResponseEntity<RomanNumeral> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/romannumeral?query=123", RomanNumeral.class);
		RomanNumeral expectedResult = new RomanNumeral("123","CXXIII");
		RomanNumeral actualResult = responseEntity.getBody();
		
		Assertions.assertEquals(expectedResult.toString(),actualResult.toString());
		
	}
	
	@Test
	public void validMediumQueryValue() throws Exception {
		
		ResponseEntity<RomanNumeral> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/romannumeral?query=1000", RomanNumeral.class);

		RomanNumeral expectedResult = new RomanNumeral("1000","M");
		RomanNumeral actualResult = responseEntity.getBody();
		
		Assertions.assertEquals(expectedResult.toString(),actualResult.toString());
		
	}
	
	@Test
	public void invalidZeroQueryValue() throws Exception {
		
		ResponseEntity<ErrorDetails> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/romannumeral?query=0", ErrorDetails.class);

		ErrorDetails result = responseEntity.getBody();
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getErrorCode(), 3);
		Assertions.assertEquals(responseEntity.getStatusCode(),HttpStatus.UNPROCESSABLE_ENTITY);
		
	}

	@Test
	public void invalidLargeQueryValue() throws Exception {

		ResponseEntity<ErrorDetails> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/romannumeral?query=4000", ErrorDetails.class);

		ErrorDetails result = responseEntity.getBody();
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getErrorCode(), 3);
		Assertions.assertEquals(responseEntity.getStatusCode(),HttpStatus.UNPROCESSABLE_ENTITY);
		
	}
	
	@Test
	public void invalidNegativeQueryValue() throws Exception {

		ResponseEntity<ErrorDetails> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/romannumeral?query=-10", ErrorDetails.class);

		ErrorDetails result = responseEntity.getBody();
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getErrorCode(), 3);
		Assertions.assertEquals(responseEntity.getStatusCode(),HttpStatus.UNPROCESSABLE_ENTITY);
		
	}

	@Test
	public void invalidDecimalQueryValue() throws Exception {
		
		ResponseEntity<ErrorDetails> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/romannumeral?query=2.5", ErrorDetails.class);

		ErrorDetails result = responseEntity.getBody();
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getErrorCode(), 2);
		Assertions.assertEquals(responseEntity.getStatusCode(),HttpStatus.UNPROCESSABLE_ENTITY);
		
	}
	
	@Test
	public void invalidLetterQueryValue() throws Exception {
		
		ResponseEntity<ErrorDetails> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/romannumeral?query=ab123", ErrorDetails.class);

		ErrorDetails result = responseEntity.getBody();
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getErrorCode(), 2);
		Assertions.assertEquals(responseEntity.getStatusCode(),HttpStatus.UNPROCESSABLE_ENTITY);
		
	}
	
	@Test
	public void invalidEmptyQueryValue() throws Exception {
		
		ResponseEntity<ErrorDetails> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/romannumeral?query=", ErrorDetails.class);

		ErrorDetails result = responseEntity.getBody();
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getErrorCode(), 1);
		Assertions.assertEquals(responseEntity.getStatusCode(),HttpStatus.BAD_REQUEST);
		
	}
	
	@Test
	public void invalidWhiteSpaceQueryValue() throws Exception {
		
		ResponseEntity<ErrorDetails> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/romannumeral?query=   ", ErrorDetails.class);

		ErrorDetails result = responseEntity.getBody();
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getErrorCode(), 1);
		Assertions.assertEquals(responseEntity.getStatusCode(),HttpStatus.BAD_REQUEST);
		
	}
	
	@Test
	public void unExpectedExceptionHappened() throws Exception {
		
		ResponseEntity<ErrorDetails> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/romannumeral?--2", ErrorDetails.class);

		ErrorDetails result = responseEntity.getBody();
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getErrorCode(), 0);
		Assertions.assertEquals(responseEntity.getStatusCode(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
