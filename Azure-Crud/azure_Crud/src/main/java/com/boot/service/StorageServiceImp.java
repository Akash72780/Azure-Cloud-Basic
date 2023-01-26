package com.boot.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;

import org.springframework.stereotype.Service;

import com.boot.model.FileEntity;
import com.boot.util.ConfigUtil;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;

@Service
public class StorageServiceImp implements StorageService {

	@Override
	public void createContainer(String container_name) throws Exception {
		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;

		try {
			storageAccount = CloudStorageAccount.parse(ConfigUtil.storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();

			// uploads the file to your container called quickstartcontainer.
			container = blobClient.getContainerReference(container_name.toLowerCase());

			// Create the container if it does not exist with public access.
			System.out.println("Creating container: " + container.getName());
//						container.createIfNotExists(new BlobRequestOptions(), new OperationContext());
			container.createIfNotExists();
		} catch (StorageException ex) {
			throw new Exception(
					String.format("Error occured", ex.getHttpStatusCode(), ex.getErrorCode(), ex.getMessage()));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	public void deleteContainer(String container_name) throws Exception {
		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		
		try {
			storageAccount = CloudStorageAccount.parse(ConfigUtil.storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();

			// uploads the file to your container called quickstartcontainer.
			container = blobClient.getContainerReference(container_name.toLowerCase());

			// Create the container if it does not exist with public access.
			System.out.println("Creating container: " + container.getName());
//						container.createIfNotExists(new BlobRequestOptions(), new OperationContext());
			container.deleteIfExists();
		} catch (StorageException ex) {
			throw new Exception(
					String.format("Error occured", ex.getHttpStatusCode(), ex.getErrorCode(), ex.getMessage()));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	@Override
	public void downloadFrmStorage(FileEntity fileEntity) {
		System.out.println("Azure Blob storage quick start sample");

		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;

		try {
			storageAccount = CloudStorageAccount.parse(ConfigUtil.storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();

			// uploads the file to your container called quickstartcontainer.
			container = blobClient.getContainerReference(fileEntity.getContainerName().toLowerCase());
			
			// Loop through each blob item in the container.
			   for (ListBlobItem blobItem : container.listBlobs()) {
			       // If the item is a blob, not a virtual directory.
			       if (blobItem instanceof CloudBlob) {
			           // Download the item and save it to a file with the same name.
				        CloudBlob blob = (CloudBlob) blobItem;
				        if(blob.getName().substring(0, blob.getName().indexOf('.')).equals(fileEntity.getFileName()))
				        	blob.download(new FileOutputStream("src\\main\\resources\\static\\download\\" + blob.getName()));
				    }
				}
			

		} catch (StorageException ex) {
			System.out.println(
					String.format("Error occured", ex.getHttpStatusCode(), ex.getErrorCode(), ex.getMessage()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void uploadInStorage(FileEntity fileEntity) {
		File sourceFile = null, downloadedFile = null;
		System.out.println("Azure Blob storage quick start sample");

		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;

		try {
			storageAccount = CloudStorageAccount.parse(ConfigUtil.storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();

			// uploads the file to your container called quickstartcontainer.
			container = blobClient.getContainerReference(fileEntity.getContainerName().toLowerCase());

			// Creating a sample file
//			sourceFile = File.createTempFile();
//			System.out.println("Creating a sample file at: " + sourceFile.toString());
//			Writer output = new BufferedWriter(new FileWriter(sourceFile));
//			output.write("Hello Azure!");
//			output.close();
			sourceFile = new File(
					fileEntity.getFileLocation() + fileEntity.getFileName() + fileEntity.getFileExtension());
			System.out.println(sourceFile.getAbsolutePath());

			// Getting a blob reference
			CloudBlockBlob blob = container.getBlockBlobReference(sourceFile.getName());

			// Creating blob and uploading file to it
			System.out.println("Uploading the sample file ");
			blob.uploadFromFile(sourceFile.getAbsolutePath());

			// Listing contents of container
//			for (ListBlobItem blobItem : container.listBlobs())
//				System.out.println("URI of blob is: " + blobItem.getUri());
//
//			downloadedFile = new File(sourceFile.getParentFile(), "downloadedFile" + fileEntity.getFileExtension());
//			blob.downloadToFile(downloadedFile.getAbsolutePath());

		} catch (StorageException ex) {
			System.out.println(
					String.format("Error occured", ex.getHttpStatusCode(), ex.getErrorCode(), ex.getMessage()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
