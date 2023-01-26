package com.boot.util;

import org.springframework.stereotype.Component;

@Component
public class ConfigUtil {
	
	public static final String iotHubConnectionString = "HostName=IoT-NxT-Hub.azure-devices.net;SharedAccessKeyName=iothubowner;SharedAccessKey=GHgKuy6Ie3IGHKliLQ9IHRJhwRuPzYhITn4s4PXZTjk=";
    public static final String serviceConnectionString = "HostName=IoT-NxT-Hub.azure-devices.net;SharedAccessKeyName=service;SharedAccessKey=n2YFdrQBhbPAkXoJEjPkLF1nx3IkPoBy7g2abqI7vu8=";
    public static final String deviceConnectionString = "HostName=IoT-NxT-Hub.azure-devices.net;DeviceId=ADCam2;SharedAccessKey=2I2qDYhIga+weie+7e4V0w==";
    public static final String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=akash000m1085107;AccountKey=vm3jdz668uiinKsWLVN9O9Mc24RoXUXRSq1gH+1/cgM70MAETRs/Vay3+hof/vSpHHSfDzSJjS4H+AStPUXvfg==;EndpointSuffix=core.windows.net";
    public static final String moduleId0 = "MonitorModule";
    public static final String moduleId1 = "ActionModule";
}
