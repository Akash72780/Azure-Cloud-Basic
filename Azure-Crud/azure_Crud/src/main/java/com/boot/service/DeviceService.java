package com.boot.service;

import java.util.HashMap;
import java.util.List;

import com.microsoft.azure.sdk.iot.service.Device;
import com.microsoft.azure.sdk.iot.service.devicetwin.DeviceTwin;
import com.microsoft.azure.sdk.iot.service.devicetwin.DeviceTwinDevice;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;

public interface DeviceService {
	
	public Device registerDevice(String device_id);
	
	public Device updateDevice(String device_id);
	
	public Device readDevice(String device_id);
	
	public void removeDevice(String device_id);

}
