package com.boot.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.boot.util.ConfigUtil;
import com.microsoft.azure.sdk.iot.service.DeliveryAcknowledgement;
import com.microsoft.azure.sdk.iot.service.FeedbackBatch;
import com.microsoft.azure.sdk.iot.service.FeedbackReceiver;
import com.microsoft.azure.sdk.iot.service.IotHubServiceClientProtocol;
import com.microsoft.azure.sdk.iot.service.Message;
import com.microsoft.azure.sdk.iot.service.ServiceClient;

@Service
public class MassageServiceImp implements MassageService{
	
	@Override
	public void cloud2DeviceMsg(String device_id,String msg) throws IOException {
		ServiceClient serviceClient=null;
		FeedbackReceiver feedbackReceiver=null;
		FeedbackBatch feedbackBatch=null;
		Message messageToSend=null;
		try {
			serviceClient=new ServiceClient(ConfigUtil.iotHubConnectionString, IotHubServiceClientProtocol.AMQPS);
			if(serviceClient!=null) {
				serviceClient.open();
				feedbackReceiver=serviceClient.getFeedbackReceiver();
				if(feedbackReceiver!=null) {
					feedbackReceiver.open();
					messageToSend = new Message(msg);
				    messageToSend.setDeliveryAcknowledgementFinal(DeliveryAcknowledgement.Full);

				    serviceClient.send(device_id, messageToSend);
				    System.out.println("Message sent to device");
				}
				feedbackBatch=feedbackReceiver.receive(1000);
				if (feedbackBatch != null) {
				      System.out.println("Message feedback received, feedback time: "
				        + feedbackBatch.getEnqueuedTimeUtc().toString());
				    }
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (feedbackReceiver!=null) {
				feedbackReceiver.close();
			}
			if (serviceClient!=null) {
				serviceClient.close();
			}
		}
	}

}
