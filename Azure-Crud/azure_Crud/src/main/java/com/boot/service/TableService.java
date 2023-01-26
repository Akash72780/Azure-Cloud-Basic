package com.boot.service;

import java.util.List;

import com.boot.model.TableEntry;
import com.boot.model.Tb_emp;

public interface TableService {
	
	public void createTable(String table);
	public void deleteTable(String table);
	public List<String> getAllTable();
	public void insertIntoTable(TableEntry tableEntity);
	public void insertAllIntoTable(TableEntry tableEntity);
	
	//query
	public List<Tb_emp> getAllData();
	public List<Tb_emp> getAllDataWithRange();
	public Tb_emp getSingleRecord();
	public Tb_emp getModifyRecord();
	public void getColumnValue();
}
