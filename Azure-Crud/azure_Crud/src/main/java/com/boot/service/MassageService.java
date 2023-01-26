package com.boot.service;

import java.io.IOException;

import com.microsoft.azure.sdk.iot.service.Message;

public interface MassageService {
	
	public void cloud2DeviceMsg(String device_id,String msg) throws IOException;

}
