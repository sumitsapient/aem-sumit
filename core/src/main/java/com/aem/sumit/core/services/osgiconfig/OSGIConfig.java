package com.aem.sumit.core.services.osgiconfig;

public interface OSGIConfig {
    public String getServiceName();
    public int getServiceCount();
    public boolean isLiveData();
    public String[] getCountries() ;
    public String getRunModes();
}
