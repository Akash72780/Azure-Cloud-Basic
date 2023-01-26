package com.boot.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boot.service.MassageService;
import com.microsoft.azure.sdk.iot.service.Message;

@RequestMapping("/msg")
@RestController
public class MassageController {
	
	@Autowired
	private MassageService massageService;
	
	@RequestMapping(value = "/{device_id}",method = RequestMethod.POST)
	public ResponseEntity<String> registerDevice(@PathVariable("device_id") String device_id,@RequestBody HashMap<String, String> msg){
		try {
			massageService.cloud2DeviceMsg(device_id,msg.get("msg"));
			return new ResponseEntity<String>("msg sent",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
