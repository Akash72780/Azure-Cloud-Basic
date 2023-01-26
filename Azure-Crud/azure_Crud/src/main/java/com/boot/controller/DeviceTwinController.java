package com.boot.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boot.service.DeviceTwinService;
import com.microsoft.azure.sdk.iot.service.devicetwin.DeviceTwinDevice;

@RequestMapping("/deviceTwin")
@RestController
public class DeviceTwinController {
	
	@Autowired
	private DeviceTwinService deviceService;
	
	@RequestMapping(value = "/twin/{device_id}",method = RequestMethod.POST)
	public ResponseEntity<DeviceTwinDevice> operationDevice(@PathVariable("device_id") String device_id,@RequestBody HashMap<String, Integer> temparature){
		System.out.println("operation device");
		try {
			return new ResponseEntity<DeviceTwinDevice>(deviceService.readTwinDevice(device_id,temparature),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/twin",method = RequestMethod.GET)
	public ResponseEntity<List<DeviceTwinDevice>> allDevice(){
		System.out.println("operation device");
		try {
			return new ResponseEntity<List<DeviceTwinDevice>>(deviceService.getAllDeviceBYquery(),HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
