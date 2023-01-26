package com.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.boot.service.DeviceService;
import com.boot.service.DeviceServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.sdk.iot.service.Device;
import com.microsoft.azure.sdk.iot.service.devicetwin.DeviceTwin;
import com.microsoft.azure.sdk.iot.service.devicetwin.DeviceTwinDevice;

@RestController
@RequestMapping("/device")
public class DeviceController {
	
	@Autowired
	private DeviceService deviceService;
	
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public ResponseEntity<String> welcome(){
		System.out.println("welcome to mindtree");
		return new ResponseEntity<String>("Welcome to mindtree",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/register/{device_id}",method = RequestMethod.GET)
	public ResponseEntity<Device> registerDevice(@PathVariable("device_id") String device_id){
		System.out.println("create device");
		try {
			return new ResponseEntity<Device>(deviceService.registerDevice(device_id),HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/update/{device_id}",method = RequestMethod.PUT)
	public ResponseEntity<Device> updateDevice(@PathVariable("device_id") String device_id){
		System.out.println("update device");
		try {
			return new ResponseEntity<Device>(deviceService.updateDevice(device_id),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/read/{device_id}",method = RequestMethod.GET)
	public ResponseEntity<Device> readDevice(@PathVariable("device_id") String device_id){
		System.out.println("read device");
		try {
			return new ResponseEntity<Device>(deviceService.readDevice(device_id),HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/remove/{device_id}",method = RequestMethod.GET)
	public ResponseEntity<String> removeDevice(@PathVariable("device_id") String device_id){
		System.out.println("remove device");
		try {
			deviceService.removeDevice(device_id);
			return new ResponseEntity<String>("Device has been removed",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Device has not been removed",HttpStatus.BAD_REQUEST);
		}
	}

}
