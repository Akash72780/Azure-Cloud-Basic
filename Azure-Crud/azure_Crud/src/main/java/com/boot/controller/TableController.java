package com.boot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boot.model.TableEntry;
import com.boot.model.Tb_emp;
import com.boot.service.TableService;

@RestController
@RequestMapping("/table")
public class TableController {
	
	@Autowired
	private TableService tableService;
	
	@RequestMapping(value = "/create_table",method = RequestMethod.PUT)
	public ResponseEntity<String> createTable(@RequestBody TableEntry tableEntity){
		System.out.println("table upload");
		try {
			tableService.createTable(tableEntity.getTableName());
			return new ResponseEntity<String>("Created",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failed to create",HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/delete_table",method = RequestMethod.PUT)
	public ResponseEntity<String> deleteTable(@RequestBody TableEntry tableEntity){
		try {
			tableService.deleteTable(tableEntity.getTableName());
			return new ResponseEntity<String>("Deleted",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failed to delete",HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/insert_employee",method = RequestMethod.PUT)
	public ResponseEntity<String> insertEmployee(@RequestBody TableEntry tableEntity){
		System.out.println("storage upload");
		try {
			tableService.insertIntoTable(tableEntity);
			return new ResponseEntity<String>("Uploaded",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failed to upload",HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/insert_employees",method = RequestMethod.PUT)
	public ResponseEntity<String> insertEmployees(@RequestBody TableEntry tableEntity){
		System.out.println("storage upload");
		try {
			tableService.insertAllIntoTable(tableEntity);
			return new ResponseEntity<String>("Uploaded",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failed to upload",HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/all_table",method = RequestMethod.GET)
	public ResponseEntity<List<String>> getAllTables(){
		System.out.println("storage upload");
		try {
			return new ResponseEntity<List<String>>(tableService.getAllTable(),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<String>>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/all_data",method = RequestMethod.GET)
	public ResponseEntity<List<Tb_emp>> getAllData(){
		System.out.println("storage upload");
		try {
			return new ResponseEntity<List<Tb_emp>>(tableService.getAllData(),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Tb_emp>>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/all_data_range",method = RequestMethod.GET)
	public ResponseEntity<List<Tb_emp>> getAllDataWithRange(){
		System.out.println("storage upload");
		try {
			return new ResponseEntity<List<Tb_emp>>(tableService.getAllDataWithRange(),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Tb_emp>>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/single_record",method = RequestMethod.GET)
	public ResponseEntity<Tb_emp> getSingleRecord(){
		System.out.println("storage upload");
		try {
			return new ResponseEntity<Tb_emp>(tableService.getSingleRecord(),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Tb_emp>(new Tb_emp(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/modify_record",method = RequestMethod.GET)
	public ResponseEntity<Tb_emp> getModifyRecord(){
		System.out.println("storage upload");
		try {
			return new ResponseEntity<Tb_emp>(tableService.getModifyRecord(),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Tb_emp>(new Tb_emp(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/column_record",method = RequestMethod.GET)
	public void getColumnRecord(){
		System.out.println("storage upload");
		try {
			tableService.getColumnValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
