package com.boot.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.stereotype.Service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.boot.util.ConfigUtil;
import com.microsoft.azure.sdk.iot.deps.serializer.FileUploadCompletionNotification;
import com.microsoft.azure.sdk.iot.deps.serializer.FileUploadSasUriRequest;
import com.microsoft.azure.sdk.iot.deps.serializer.FileUploadSasUriResponse;
import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;

@Service
public class FileServiceImp implements FileService {

	@Override
	public void fileUpload2Cloud() throws IOException {
		IotHubClientProtocol protocol = IotHubClientProtocol.MQTT;
		DeviceClient deviceClient = null;
		String fullFileName = "C:\\Users\\M1085107\\eclipse-workspace\\demo.txt";

		try {
			deviceClient = new DeviceClient(ConfigUtil.deviceConnectionString, protocol);
			File file = new File(fullFileName);
			if (file.isDirectory())
				throw new IllegalArgumentException("Path is a directory");

			FileUploadSasUriResponse sasUriResponse = deviceClient
					.getFileUploadSasUri(new FileUploadSasUriRequest(file.getName()));
			

            System.out.println("Successfully got SAS URI from IoT Hub");
            System.out.println("Correlation Id: " + sasUriResponse.getCorrelationId());
            System.out.println("Container name: " + sasUriResponse.getContainerName());
            System.out.println("Blob name: " + sasUriResponse.getBlobName());
            System.out.println("Blob Uri: " + sasUriResponse.getBlobUri());


			try {
				BlobClient blobClient = new BlobClientBuilder().endpoint(sasUriResponse.getBlobUri().toString())
						.buildClient();

				blobClient.uploadFromFile(fullFileName);
			} catch (Exception e) {
				System.out.println("Exception encountered while uploading file to blob: " + e.getMessage());
				FileUploadCompletionNotification completionNotification = new FileUploadCompletionNotification(
						sasUriResponse.getCorrelationId(), false);
				deviceClient.completeFileUpload(completionNotification);

				System.out.println(
						"Notified IoT Hub that the SAS URI can be freed and that the file upload was a failure.");

				deviceClient.closeNow();
			}

			System.out.println("Successfully uploaded file to Azure Storage.");

			FileUploadCompletionNotification completionNotification = new FileUploadCompletionNotification(
					sasUriResponse.getCorrelationId(), true);
			deviceClient.completeFileUpload(completionNotification);
			System.out.println(
					"Successfully notified IoT Hub that the SAS URI can be freed, and that the file upload was a success");

		} catch (Exception e) {
			System.out.println("Shutting down...");
			e.printStackTrace();
		} finally {
			if(deviceClient!=null)
				deviceClient.closeNow();
		}
	}

}
