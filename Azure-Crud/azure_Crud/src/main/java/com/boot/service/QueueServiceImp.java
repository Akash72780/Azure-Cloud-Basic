package com.boot.service;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import com.azure.storage.queue.models.QueueStorageException;
import com.boot.util.ConfigUtil;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import com.microsoft.azure.storage.queue.CloudQueueMessage;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;

@Service
public class QueueServiceImp implements QueueService{

	@Override
	public void createQueue(String queueName) {
		try {

			// Instantiate a QueueClient which will be
			// used to create and manipulate the queue
			// Create a TableServiceClient with a connection string.
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(ConfigUtil.storageConnectionString);

			// Create the table client.
			CloudQueueClient queueClient = storageAccount.createCloudQueueClient();
			
			CloudQueue cloudQueue = queueClient.getQueueReference(queueName);
			
			
			cloudQueue.createIfNotExists();

		} catch (Exception e) {
			// Output the exception message and stack trace
			System.out.println("Message: " + e.getMessage());
		} 
	}
	
	@Override
	public void deleteQueue(String queueName) {
		try {

			// Instantiate a QueueClient which will be
			// used to create and manipulate the queue
			// Create a TableServiceClient with a connection string.
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(ConfigUtil.storageConnectionString);

			// Create the table client.
			CloudQueueClient queueClient = storageAccount.createCloudQueueClient();
			
			CloudQueue cloudQueue = queueClient.getQueueReference(queueName);
			
			
			cloudQueue.deleteIfExists();

		} catch (Exception e) {
			// Output the exception message and stack trace
			System.out.println("Message: " + e.getMessage());
		} 
	}
	
	@Override
	public void addMassage(HashMap<String, String> request) {
		try
		{
			// Retrieve storage account from connection-string.
			CloudStorageAccount storageAccount =
		       CloudStorageAccount.parse(ConfigUtil.storageConnectionString);

			// Create the queue client.
			CloudQueueClient queueClient = storageAccount.createCloudQueueClient();

			// Retrieve a reference to a queue.
			CloudQueue queue = queueClient.getQueueReference(request.get("queue"));

			// Create a message and add it to the queue.
			CloudQueueMessage message = new CloudQueueMessage(request.get("msg"));
			queue.addMessage(message);
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
	}
	
	@Override
	public String peekMassage(String queueName) {
		String result=null;
		try
		{
			// Retrieve storage account from connection-string.
			CloudStorageAccount storageAccount =
		       CloudStorageAccount.parse(ConfigUtil.storageConnectionString);

			// Create the queue client.
			CloudQueueClient queueClient = storageAccount.createCloudQueueClient();

			// Retrieve a reference to a queue.
			CloudQueue queue = queueClient.getQueueReference(queueName);

			// Peek at the next message.
			CloudQueueMessage peekedMessage = queue.peekMessage();

			// Output the message value.
			if (peekedMessage != null)
			{
			  result=peekedMessage.getMessageContentAsString();
		   }
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<CloudQueueMessage> allMassage(String queueName) {
		String result=null;
		List<CloudQueueMessage> peekedMessage=null;
		try
		{
			// Retrieve storage account from connection-string.
			CloudStorageAccount storageAccount =
		       CloudStorageAccount.parse(ConfigUtil.storageConnectionString);

			// Create the queue client.
			CloudQueueClient queueClient = storageAccount.createCloudQueueClient();

			// Retrieve a reference to a queue.
			CloudQueue queue = queueClient.getQueueReference(queueName);

			// Peek at the next message.
			peekedMessage = (List<CloudQueueMessage>) queue.peekMessages(3);

			
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
		return peekedMessage;
	}
	

}
