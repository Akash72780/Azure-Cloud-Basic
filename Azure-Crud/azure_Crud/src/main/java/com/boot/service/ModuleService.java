package com.boot.service;

import java.util.List;

import com.microsoft.azure.sdk.iot.service.Module;

public interface ModuleService {
	
	public Module createModule(String device_id, int module_id) throws Exception;
	
	public Module GetModule(String device_id, int module_id) throws Exception;
	
	public List<Module> GetAllModule(String device_id) throws Exception;
	
	public Module UpdateModule(String device_id, int module_id) throws Exception;
	
	public void RemoveModule(String device_id, int module_id) throws Exception;

}
