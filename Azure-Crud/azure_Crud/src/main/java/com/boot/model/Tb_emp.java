package com.boot.model;


import com.microsoft.azure.storage.table.TableServiceEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Tb_emp extends TableServiceEntity {
	
	public Tb_emp() {}
	
	public Tb_emp(String lastName, String firstName) {
        this.partitionKey = lastName;
        this.rowKey = firstName;
    }

	private String emp_cd;
	private String emp_name;
	private String emp_salary;
	public String getEmp_cd() {
		return emp_cd;
	}
	public void setEmp_cd(String emp_cd) {
		this.emp_cd = emp_cd;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getEmp_salary() {
		return emp_salary;
	}
	public void setEmp_salary(String emp_salary) {
		this.emp_salary = emp_salary;
	}

	@Override
	public String toString() {
		return "Tb_emp [emp_cd=" + emp_cd + ", emp_name=" + emp_name + ", emp_salary=" + emp_salary + "]";
	}
	
}
