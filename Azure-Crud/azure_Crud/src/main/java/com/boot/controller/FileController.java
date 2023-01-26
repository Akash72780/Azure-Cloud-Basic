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

import com.boot.model.FileEntity;
import com.boot.service.FileService;
import com.boot.service.StorageService;

@RestController
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private StorageService storageService;
	
	@RequestMapping(value = "/create_container",method = RequestMethod.PUT)
	public ResponseEntity<String> createContainer(@RequestBody FileEntity fileEntity){
		System.out.println("storage upload");
		try {
			storageService.createContainer(fileEntity.getContainerName());
			return new ResponseEntity<String>("Created",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failed to create",HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/delete_container",method = RequestMethod.PUT)
	public ResponseEntity<String> deleteContainer(@RequestBody FileEntity fileEntity){
		try {
			storageService.deleteContainer(fileEntity.getContainerName());
			return new ResponseEntity<String>("Deleted",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failed to delete",HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/uploadFile",method = RequestMethod.PUT)
	public ResponseEntity<String> upload2Instorage(@RequestBody FileEntity fileEntity){
		System.out.println("storage upload");
		try {
			storageService.uploadInStorage(fileEntity);
			return new ResponseEntity<String>("Uploaded",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failed to upload",HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/downloadFile",method = RequestMethod.POST)
	public ResponseEntity<String> downloadFrmStorage(@RequestBody FileEntity fileEntity){
		System.out.println("storage upload");
		try {
			storageService.downloadFrmStorage(fileEntity);
			return new ResponseEntity<String>("Downloaded",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failed to download",HttpStatus.BAD_REQUEST);
		}
	}

}
