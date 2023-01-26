package com.boot.service;

import java.util.HashMap;
import java.util.List;

import com.microsoft.azure.sdk.iot.service.devicetwin.DeviceTwinDevice;

public interface DeviceTwinService {

	public DeviceTwinDevice readTwinDevice(String device_id,HashMap<String, Integer> temp);

	public List<DeviceTwinDevice> getAllDeviceBYquery();

}
