package com.springboot.romannumeral.exception;


import java.util.Date;

/**
* The ErrorDetails class keeps all detail information
* of errors that can be returned when '/romannumeral'
* GET end point is called.
* @author  Yan Zhou
* @version 1.0
* @since   2021-02-15 
*/
public class ErrorDetails {
	
	
	private Date timestamp;
	private Integer errorCode;
	private String mesasge;
	private String details;
	
	/**
	* The constructor of ErrorDetails class.
	* @param timestamp : Date time stamp when error happens.
	* @param errorCode: The integer representation of error>
	* 	0: Unexpected exception is seen when handle the request.
	*   1: Parameter query's value is either empty or only contains white space.
	*   2: Parameter query's value is not a correct integer format.
	*   3: Parameter query's value is out of the supported range: [1,3999].
	* @param message: description of the error.
	* @param details: detail information about the request. 
	* 		 currently listing the URI of the request.
	*/	
	public ErrorDetails(Date timestamp, Integer errorCode, String mesasge, String details) {
		super();
		this.timestamp = timestamp;
		this.errorCode = errorCode;
		this.mesasge = mesasge;
		this.details = details;
	}
	
	/**
	* getter for timestamp.
	*/
	public Date getTimestamp() {
		return timestamp;
	}
	
	/**
	* setter for timestamp.
	* @param timestamp
	*/
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	* getter for errorCode.
	*/
	public Integer getErrorCode() {
		return errorCode;
	}
	
	/**
	* setter for errorCode.
	* @param errorCode
	*/
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	
	/**
	* getter for message.
	*/
	public String getMesasge() {
		return mesasge;
	}
	
	/**
	* setter for message.
	* @param mesasge
	*/
	public void setMesasge(String mesasge) {
		this.mesasge = mesasge;
	}
	
	/**
	* getter for details.
	*/
	public String getDetails() {
		return details;
	}
	
	/**
	* setter for details.
	* @param details
	*/
	public void setDetails(String details) {
		this.details = details;
	}
	
	
	
	

}
