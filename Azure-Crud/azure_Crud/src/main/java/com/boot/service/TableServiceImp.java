package com.boot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.azure.data.tables.TableClient;
import com.azure.data.tables.TableClientBuilder;
import com.azure.data.tables.TableServiceClient;
import com.azure.data.tables.TableServiceClientBuilder;
import com.azure.data.tables.models.TableEntity;
import com.azure.data.tables.models.TableTransactionAction;
import com.azure.data.tables.models.TableTransactionActionType;
import com.boot.model.TableEntry;
import com.boot.model.Tb_emp;
import com.boot.util.ConfigUtil;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.EntityProperty;
import com.microsoft.azure.storage.table.EntityResolver;
import com.microsoft.azure.storage.table.TableBatchOperation;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableQuery;
import com.microsoft.azure.storage.table.TableQuery.Operators;
import com.microsoft.azure.storage.table.TableQuery.QueryComparisons;
import com.microsoft.azure.storage.table.TableServiceEntity;

@Service
public class TableServiceImp implements TableService {

	@Override
	public void createTable(String table) {

		try {
			// Create a TableServiceClient with a connection string.
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(ConfigUtil.storageConnectionString);

			// Create the table client.
			CloudTableClient tableClient = storageAccount.createCloudTableClient();

			// Create the table if it doesn't exist.
			CloudTable cloudTable = tableClient.getTableReference(table);
			cloudTable.createIfNotExists();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void deleteTable(String table) {

		try {
			// Create a TableServiceClient with a connection string.
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(ConfigUtil.storageConnectionString);

			// Create the table client.
			CloudTableClient tableClient = storageAccount.createCloudTableClient();

			// Create the table if it doesn't exist.
			CloudTable cloudTable = tableClient.getTableReference(table);
			cloudTable.deleteIfExists();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<String> getAllTable() {
		List<String> allTable = new ArrayList<String>();

		try {
			// Retrieve storage account from connection-string.
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(ConfigUtil.storageConnectionString);

			// Create the table client.
			CloudTableClient tableClient = storageAccount.createCloudTableClient();

			tableClient.listTables().forEach(tableItem -> allTable.add(tableItem));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return allTable;
	}

	@Override
	public void insertIntoTable(TableEntry tableEntry) {

		try {
			// Create a TableClient with a connection string and a table name.
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(ConfigUtil.storageConnectionString);

			// Create the table client.
			CloudTableClient tableClient = storageAccount.createCloudTableClient();

			// Create a cloud table object for the table.
			CloudTable cloudTable = tableClient.getTableReference(tableEntry.getTableName());
			// Create a cloud table object for the table.
			Tb_emp employee = new Tb_emp("patitionKey", "rowKey");
			employee.setEmp_cd(tableEntry.getEmployees().get(0).getEmp_cd());
			employee.setEmp_name(tableEntry.getEmployees().get(0).getEmp_name());
			employee.setEmp_salary(tableEntry.getEmployees().get(0).getEmp_salary());

			// Create an operation to add the new customer to the people table.
			TableOperation insertEmployee = TableOperation.insertOrReplace(employee);

			// Submit the operation to the table service.
			cloudTable.execute(insertEmployee);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void insertAllIntoTable(TableEntry tableEntry) {
		// Define a batch operation.
		TableBatchOperation batchOperation = new TableBatchOperation();
		Tb_emp employees = null;

		try {
			// Create a TableClient with a connection string and a table name.
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(ConfigUtil.storageConnectionString);

			// Create the table client.
			CloudTableClient tableClient = storageAccount.createCloudTableClient();

			// Create a cloud table object for the table.
			CloudTable cloudTable = tableClient.getTableReference(tableEntry.getTableName());

			String partitionKey = "patitionKey";

			for (int i = 0; i < tableEntry.getEmployees().size(); i++) {
				employees = new Tb_emp(partitionKey, "100" + i);
				employees.setEmp_cd(tableEntry.getEmployees().get(i).getEmp_cd());
				employees.setEmp_name(tableEntry.getEmployees().get(i).getEmp_name());
				employees.setEmp_salary(tableEntry.getEmployees().get(i).getEmp_salary());
				batchOperation.insertOrReplace(employees);
			}

			cloudTable.execute(batchOperation);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public List<Tb_emp> getAllData(){
		List<Tb_emp> employees=new ArrayList<Tb_emp>();
		try
		{
			// Define constants for filters.
			final String PARTITION_KEY = "PartitionKey";
			final String ROW_KEY = "rowKey";
			final String TIMESTAMP = "Timestamp";

			// Retrieve storage account from connection-string.
			CloudStorageAccount storageAccount =
		       CloudStorageAccount.parse(ConfigUtil.storageConnectionString);

			// Create the table client.
			CloudTableClient tableClient = storageAccount.createCloudTableClient();

		   // Create a cloud table object for the table.
		   CloudTable cloudTable = tableClient.getTableReference("Deploying");

			// Create a filter condition where the partition key is "Smith".
			String partitionFilter = TableQuery.generateFilterCondition(
		       PARTITION_KEY,
		       QueryComparisons.EQUAL,
		       "patitionKey");

		   // Specify a partition query, using "Smith" as the partition key filter.
		   TableQuery<Tb_emp> partitionQuery =
		       TableQuery.from(Tb_emp.class)
		       .where(partitionFilter);

		    // Loop through the results, displaying information about the entity.
		    for (Tb_emp employee : cloudTable.execute(partitionQuery)) {
		        employees.add(employee);
		   }
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return employees;
	}
	
	@Override
	public List<Tb_emp> getAllDataWithRange(){
		List<Tb_emp> employees=new ArrayList<Tb_emp>();
		try
		{
			// Define constants for filters.
			final String PARTITION_KEY = "PartitionKey";
			final String ROW_KEY = "RowKey";
			final String TIMESTAMP = "Timestamp";

			// Retrieve storage account from connection-string.
			CloudStorageAccount storageAccount =
		       CloudStorageAccount.parse(ConfigUtil.storageConnectionString);

		   // Create the table client.
		   CloudTableClient tableClient = storageAccount.createCloudTableClient();

		   // Create a cloud table object for the table.
		   CloudTable cloudTable = tableClient.getTableReference("Deploying");

			// Create a filter condition where the partition key is "Smith".
			String partitionFilter = TableQuery.generateFilterCondition(
		       PARTITION_KEY,
		       QueryComparisons.EQUAL,
		       "patitionKey");

			// Create a filter condition where the row key is less than the letter "E".
			String rowFilter = TableQuery.generateFilterCondition(
		       ROW_KEY,
		       QueryComparisons.LESS_THAN,
		       "1004");

			// Combine the two conditions into a filter expression.
			String combinedFilter = TableQuery.combineFilters(partitionFilter,
		        Operators.AND, rowFilter);

			// Specify a range query, using "Smith" as the partition key,
			// with the row key being up to the letter "E".
			TableQuery<Tb_emp> rangeQuery =
		       TableQuery.from(Tb_emp.class)
		       .where(combinedFilter);

			// Loop through the results, displaying information about the entity
		    for (Tb_emp employee : cloudTable.execute(rangeQuery)) {
		        employees.add(employee);
		    }
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
		return employees;
	}
	
	@Override
	public Tb_emp getSingleRecord() {
		Tb_emp employee=null;
		try
		{
			// Retrieve storage account from connection-string.
			CloudStorageAccount storageAccount =
		       CloudStorageAccount.parse(ConfigUtil.storageConnectionString);

			// Create the table client.
			CloudTableClient tableClient = storageAccount.createCloudTableClient();

			// Create a cloud table object for the table.
			CloudTable cloudTable = tableClient.getTableReference("Deploying");

			// Retrieve the entity with partition key of "Smith" and row key of "Jeff"
			TableOperation result =
		       TableOperation.retrieve("patitionKey", "1002", Tb_emp.class);

		   // Submit the operation to the table service and get the specific entity.
		   employee =
				cloudTable.execute(result).getResultAsType();

		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
		return employee;
	}
	
	@Override
	public Tb_emp getModifyRecord() {
		Tb_emp employee=null;
		
		try
		{
			// Retrieve storage account from connection-string.
			CloudStorageAccount storageAccount =
		       CloudStorageAccount.parse(ConfigUtil.storageConnectionString);

			// Create the table client.
			CloudTableClient tableClient = storageAccount.createCloudTableClient();

			// Create a cloud table object for the table.
			CloudTable cloudTable = tableClient.getTableReference("Deploying");

			// Retrieve the entity with partition key of "Smith" and row key of "Jeff".
			TableOperation result =
		       TableOperation.retrieve("patitionKey", "1002", Tb_emp.class);

			// Submit the operation to the table service and get the specific entity.
			employee =
			  cloudTable.execute(result).getResultAsType();

			System.out.println("Before modify "+employee);
			// Specify a new phone number.
			employee.setEmp_cd("1001");

			// Create an operation to replace the entity.
			TableOperation replaceEntity = TableOperation.replace(employee);

			// Submit the operation to the table service.
			cloudTable.execute(replaceEntity);
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
		return employee;
	}
	
	@Override
	public void getColumnValue() {
		
		try
		{
		    // Retrieve storage account from connection-string.
		    CloudStorageAccount storageAccount =
		        CloudStorageAccount.parse(ConfigUtil.storageConnectionString);

			// Create the table client.
			CloudTableClient tableClient = storageAccount.createCloudTableClient();

			// Create a cloud table object for the table.
			CloudTable cloudTable = tableClient.getTableReference("Deploying");

			// Define a projection query that retrieves only the Email property
			TableQuery<Tb_emp> projectionQuery =
		       TableQuery.from(Tb_emp.class)
		       .select(new String[] {"Emp_name"});
			

			// Define a Entity resolver to project the entity to the Email value.
			EntityResolver<String> emailResolver = new EntityResolver<String>() {

				@Override
				public String resolve(String partitionKey, String rowKey, Date timeStamp,
						HashMap<String, EntityProperty> properties, String etag) throws StorageException {
					// TODO Auto-generated method stub
					return properties.get("Emp_name").getValueAsString();
				}
		        
		    };

		    // Loop through the results, displaying the Email values.
		    for (String projectedString :
		        cloudTable.execute(projectionQuery, emailResolver)) {
		            System.out.println(projectedString);
		    }
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
	}

}
