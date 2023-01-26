package com.boot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.boot.util.ConfigUtil;
import com.google.gson.JsonSyntaxException;
import com.microsoft.azure.sdk.iot.service.RegistryManager;
import com.microsoft.azure.sdk.iot.service.devicetwin.DeviceTwin;
import com.microsoft.azure.sdk.iot.service.devicetwin.DeviceTwinDevice;
import com.microsoft.azure.sdk.iot.service.devicetwin.Pair;
import com.microsoft.azure.sdk.iot.service.devicetwin.Query;
import com.microsoft.azure.sdk.iot.service.devicetwin.SqlQuery;
import com.microsoft.azure.sdk.iot.service.exceptions.IotHubException;

@Service
public class DeviceTwinServiceImp implements DeviceTwinService{
	
	@Override
	public DeviceTwinDevice readTwinDevice(String device_id,HashMap<String, Integer> temp) {
		DeviceTwin device = null;
		DeviceTwinDevice deviceTwin=null;
		RegistryManager manager = null;
		try {
			Set<Pair> properties=new HashSet<Pair>();
			Set<String> keys=temp.keySet();
			for (String key : keys) {
				properties.add(new Pair(key,temp.get(key) ));
			}
			device = new DeviceTwin(ConfigUtil.iotHubConnectionString);
			deviceTwin=new DeviceTwinDevice(device_id);
			device.getTwin(deviceTwin);
			deviceTwin.setTags(properties);
			device.updateTwin(deviceTwin);
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
		return deviceTwin;
	}
	
	@Override
	public List<DeviceTwinDevice> getAllDeviceBYquery(){
		List<DeviceTwinDevice> allDevice=new ArrayList<DeviceTwinDevice>();
		DeviceTwin deviceClient = null;
		RegistryManager manager = null;
		try {
			SqlQuery sqlQuery = SqlQuery.createSqlQuery("*", SqlQuery.FromType.DEVICES, "tags.temparature=97.3", null);
			deviceClient = new DeviceTwin(ConfigUtil.iotHubConnectionString);
			
			Query twinQuery = deviceClient.queryTwin(sqlQuery.getQuery(), 100);
			while (deviceClient.hasNextDeviceTwin(twinQuery)) {
			  allDevice.add(deviceClient.getNextDeviceTwin(twinQuery));
			}
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
		return allDevice;
	}

}
