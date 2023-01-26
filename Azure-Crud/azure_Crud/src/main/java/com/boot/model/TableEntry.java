package com.boot.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableEntry {
	
	private String tableName;
	private List<Tb_emp> employees;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<Tb_emp> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Tb_emp> employees) {
		this.employees = employees;
	}
	
	

}
