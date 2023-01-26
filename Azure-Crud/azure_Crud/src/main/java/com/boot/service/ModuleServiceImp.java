package com.boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boot.util.ConfigUtil;
import com.microsoft.azure.sdk.iot.service.Module;
import com.microsoft.azure.sdk.iot.service.RegistryManager;
import com.microsoft.azure.sdk.iot.service.auth.AuthenticationType;
import com.microsoft.azure.sdk.iot.service.auth.SymmetricKey;

@Service
public class ModuleServiceImp implements ModuleService{

	@Override
	public Module createModule(String device_id, int module_id) throws Exception {
		RegistryManager manager = null;
		Module module =null;
		String moduleId;
		if (module_id == 0)
			moduleId = ConfigUtil.moduleId0;
		else
			moduleId = ConfigUtil.moduleId1;
		try {
			manager = new RegistryManager(ConfigUtil.iotHubConnectionString);
			module = Module.createModule(device_id, moduleId, AuthenticationType.SAS);
			module = manager.addModule(module);
			System.out.println("Module created: " + module.getId());
		} catch (Exception iote) {
			iote.printStackTrace();
		}finally {
			if (manager!=null) {
				manager.close();
			}
		}
		return module;
	}
	
	@Override
	public Module GetModule(String device_id, int module_id) throws Exception{
    	RegistryManager manager = null;
        Module returnModule=null;
        String moduleId;
        if (module_id == 0)
			moduleId = ConfigUtil.moduleId0;
		else
			moduleId = ConfigUtil.moduleId1;
        try
        {
        	manager = new RegistryManager(ConfigUtil.iotHubConnectionString);
            returnModule = manager.getModule(device_id,moduleId);
        }catch (Exception iote){
            iote.printStackTrace();
        }finally {
			if (manager!=null) {
				manager.close();
			}
		}
        return returnModule;

    }
    
	@Override
	public List<Module> GetAllModule(String device_id) throws Exception{
    	RegistryManager manager = null;
    	List<Module> allModule=null;
    	
    	try {
    		manager = new RegistryManager(ConfigUtil.iotHubConnectionString);
    		allModule=manager.getModulesOnDevice(device_id);
    	}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (manager!=null) {
				manager.close();
			}
		}
    	return allModule;
    }

	@Override
	public Module UpdateModule(String device_id, int module_id) throws Exception
    {
        String primaryKey = ",mkjhasjdhoiKHJFHFJSgd9789aw";
        String secondaryKey = ",jmjagdkjgakudgsykjBVDKJHadtywq";
        RegistryManager manager = null;
        Module oldModule=null;
        String moduleId;
        if (module_id == 0)
			moduleId = ConfigUtil.moduleId0;
		else
			moduleId = ConfigUtil.moduleId1;
        try
        {
        	manager = new RegistryManager(ConfigUtil.iotHubConnectionString);
//        	newModule =  Module.createModule(device_id, moduleId, AuthenticationType.SAS);
        	oldModule=manager.getModule(device_id, moduleId);
        	SymmetricKey sm=new SymmetricKey();
        	sm.setPrimaryKeyFinal(primaryKey);
        	sm.setSecondaryKeyFinal(secondaryKey);
        	oldModule.setSymmetricKey(sm);
        	oldModule=manager.updateModule(oldModule);
        	
        }catch (Exception iote){
            iote.printStackTrace();
        }finally {
			if (manager!=null) {
				manager.close();
			}
		}
        return oldModule;
    }
	
	@Override
	public void RemoveModule(String device_id, int module_id) throws Exception{
    	RegistryManager manager = null;
    	String moduleId;
        if (module_id == 0)
			moduleId = ConfigUtil.moduleId0;
		else
			moduleId = ConfigUtil.moduleId1;
        try
        {
        	manager = new RegistryManager(ConfigUtil.iotHubConnectionString);
        	manager.removeModule(device_id, moduleId);
        }
        catch (Exception iote)
        {
            iote.printStackTrace();
        }
    }

}
