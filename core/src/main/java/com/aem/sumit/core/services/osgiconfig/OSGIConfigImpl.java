package com.aem.sumit.core.services.osgiconfig;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(immediate = true,service = OSGIConfig.class)
@Designate(ocd=GeeksOSGiConfig.class)
public class OSGIConfigImpl implements OSGIConfig{

    private String name;
    private int count;
    private boolean isLive;
    private String[] countries;
    private String mode;

    @Activate
    protected void activate(GeeksOSGiConfig config) {
    name = config.serviceName();
    count = config.serviceCount();
    isLive = config.liveData();
    countries = config.countries();
    mode = config.runModes();
    }


    @Override
    public String getServiceName() {
        return name;
    }

    @Override
    public int getServiceCount() {
        return count;
    }

    @Override
    public boolean isLiveData() {
        return isLive;
    }

    @Override
    public String[] getCountries() {
        return countries;
    }

    @Override
    public String getRunModes() {
        return mode;
    }
}
