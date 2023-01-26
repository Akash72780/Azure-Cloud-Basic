package com.boot.service;

import com.boot.model.FileEntity;

public interface StorageService {
	
	public void createContainer(String container_name) throws Exception;
	
	public void deleteContainer(String container_name) throws Exception;
	
	public void uploadInStorage(FileEntity fileEntity);
	
	public void downloadFrmStorage(FileEntity fileEntity);

}
