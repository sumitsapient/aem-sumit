package com.aem.sumit.core.services.osgiconfig;

import java.util.List;

public interface DemoOSGiFactory {
    public String getServiceName();
    public String getServiceURL();
    public int getServiceID();
    public List<DemoOSGiFactory> getAllConfig();
}
