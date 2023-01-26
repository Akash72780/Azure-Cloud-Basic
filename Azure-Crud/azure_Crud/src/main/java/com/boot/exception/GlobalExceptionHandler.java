package com.boot.exception;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger log=LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> illegalError(IllegalArgumentException ie){
		Map<String,String> errors=new HashMap<String,String>();
		log.error(ie.getMessage());
		errors.put("Error", ie.getMessage());
		return new ResponseEntity<Map<String,String>>(errors,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(JsonParseException.class)
	public ResponseEntity<Map<String, String>> jsonParseError(JsonParseException je){
		Map<String,String> errors=new HashMap<String,String>();
		log.error(je.getMessage());
		errors.put("Error", je.getMessage());
		return new ResponseEntity<Map<String,String>>(errors,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IotHubException.class)
	public ResponseEntity<Map<String, String>> jsonParseError(IotHubException ie){
		Map<String,String> errors=new HashMap<String,String>();
		log.error(ie.getMessage());
		errors.put("Error", ie.getMessage());
		return new ResponseEntity<Map<String,String>>(errors,HttpStatus.BAD_REQUEST);
	}

}
