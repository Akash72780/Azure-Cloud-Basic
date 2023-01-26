package com.boot.service;

import java.util.HashMap;
import java.util.List;

import com.microsoft.azure.storage.queue.CloudQueueMessage;

public interface QueueService {
	
	public void createQueue(String queueName);
	public void deleteQueue(String queueName);
	
	//
	
	public void addMassage(HashMap<String, String> request);
	public String peekMassage(String queueName);
	public List<CloudQueueMessage> allMassage(String queueName);

}
