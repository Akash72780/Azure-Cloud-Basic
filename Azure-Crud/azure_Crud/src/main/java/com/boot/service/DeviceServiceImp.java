package com.boot.service;

import org.springframework.stereotype.Service;

import com.boot.util.ConfigUtil;
import com.google.gson.JsonSyntaxException;
import com.microsoft.azure.sdk.iot.service.Device;
import com.microsoft.azure.sdk.iot.service.DeviceStatus;
import com.microsoft.azure.sdk.iot.service.RegistryManager;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;

import java.io.IOException;

@Service
public class DeviceServiceImp implements DeviceService {

	@Override
	public Device registerDevice(String device_id) {
		Device device = null;
		RegistryManager manager = null;
		try {
			device = Device.createFromId(device_id, DeviceStatus.Enabled, null);
			manager = new RegistryManager(ConfigUtil.iotHubConnectionString);
			device = manager.addDevice(device);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException | IOException | IotHubException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(manager!=null)
				manager.close();
		}

		return device;
	}

	@Override
	public Device updateDevice(String device_id) {
		Device device = null;
		RegistryManager manager = null;
		try {
			manager = new RegistryManager(ConfigUtil.iotHubConnectionString);
			device = manager.getDevice(device_id);
			device.setStatus(DeviceStatus.Disabled);
			device=manager.updateDevice(device);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException | IOException | IotHubException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(manager!=null)
				manager.close();
		}

		return device;
	}

	@Override
	public Device readDevice(String device_id) {
		Device device = null;
		RegistryManager manager = null;
		try {
			manager = new RegistryManager(ConfigUtil.iotHubConnectionString);
			device = manager.getDevice(device_id);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException | IOException | IotHubException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(manager!=null)
				manager.close();
		}
		return device;
	}

	@Override
	public void removeDevice(String device_id) {
		RegistryManager manager = null;
		try {
			manager = new RegistryManager(ConfigUtil.iotHubConnectionString);
			manager.removeDevice(device_id);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException | IOException | IotHubException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(manager!=null)
				manager.close();
		}

	}

}
