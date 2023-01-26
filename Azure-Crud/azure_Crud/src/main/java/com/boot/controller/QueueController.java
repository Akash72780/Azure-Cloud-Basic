package com.boot.controller;

import java.util.ArrayList;
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

import com.boot.service.QueueService;
import com.microsoft.azure.sdk.iot.service.Module;
import com.microsoft.azure.storage.queue.CloudQueueMessage;

@RestController
@RequestMapping("/queue")
public class QueueController {
	
	@Autowired
	private QueueService queueService;
	
	@RequestMapping(value = "/create/{queueName}",method = RequestMethod.GET)
	public ResponseEntity<String> createQueue(@PathVariable("queueName") String queueName){
		System.out.println("create queue");
		try {
			queueService.createQueue(queueName);
			return new ResponseEntity<String>("Created",HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Not created",HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value = "/delete/{queueName}",method = RequestMethod.GET)
	public ResponseEntity<String> deleteQueue(@PathVariable("queueName") String queueName){
		System.out.println("delete queue");
		try {
			queueService.deleteQueue(queueName);
			return new ResponseEntity<String>("Deleted",HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Not deleted",HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value = "/addMassage",method = RequestMethod.PUT)
	public ResponseEntity<String> addMassage(@RequestBody HashMap<String, String> request){
		System.out.println("Add queue");
		try {
			queueService.addMassage(request);
			return new ResponseEntity<String>("Added",HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Not added",HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value = "/peekMassage/{queueName}",method = RequestMethod.GET)
	public ResponseEntity<String> peekMassage(@PathVariable("queueName") String queueName){
		System.out.println("peek queue");
		try {
			return new ResponseEntity<String>(queueService.peekMassage(queueName),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Not peek",HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value = "/allMassage/{queueName}",method = RequestMethod.GET)
	public ResponseEntity<List<CloudQueueMessage>> allMassage(@PathVariable("queueName") String queueName){
		System.out.println("peek all queues");
		try {
			return new ResponseEntity<List<CloudQueueMessage>>(queueService.allMassage(queueName),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<CloudQueueMessage>>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
		}
		
	}

}
