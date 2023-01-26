package com.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.microsoft.azure.sdk.iot.service.Module;

import com.boot.service.ModuleService;
import com.microsoft.azure.sdk.iot.service.Device;

@RestController
@RequestMapping("/module")
public class ModuleController {
	
	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping(value = "/add/{device_id}/{module_id}",method = RequestMethod.GET)
	public ResponseEntity<Module> createModule(@PathVariable("device_id") String device_id, @PathVariable("module_id") String module_id){
		System.out.println("create device");
		try {
			return new ResponseEntity<Module>(moduleService.createModule(device_id,Integer.parseInt(module_id)),HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/get/{device_id}/{module_id}",method = RequestMethod.GET)
	public ResponseEntity<Module> getModule(@PathVariable("device_id") String device_id, @PathVariable("module_id") String module_id){
		System.out.println("create device");
		try {
			return new ResponseEntity<Module>(moduleService.GetModule(device_id,Integer.parseInt(module_id)),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/getAll/{device_id}",method = RequestMethod.GET)
	public ResponseEntity<List<Module>> getModule(@PathVariable("device_id") String device_id){
		System.out.println("create device");
		try {
			return new ResponseEntity<List<Module>>(moduleService.GetAllModule(device_id),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/update/{device_id}/{module_id}",method = RequestMethod.GET)
	public ResponseEntity<Module> updateModule(@PathVariable("device_id") String device_id, @PathVariable("module_id") String module_id){
		System.out.println("create device");
		try {
			return new ResponseEntity<Module>(moduleService.UpdateModule(device_id,Integer.parseInt(module_id)),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/remove/{device_id}/{module_id}",method = RequestMethod.GET)
	public ResponseEntity<String> removeModule(@PathVariable("device_id") String device_id, @PathVariable("module_id") String module_id){
		System.out.println("create device");
		try {
			moduleService.RemoveModule(device_id,Integer.parseInt(module_id));
			return new ResponseEntity<String>("Module has been removed",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
