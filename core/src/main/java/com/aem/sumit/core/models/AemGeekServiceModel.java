package com.aem.sumit.core.models;


import com.aem.sumit.core.services.AemGeekServiceA;
import com.aem.sumit.core.services.osgiconfig.DemoOSGiFactory;
import com.aem.sumit.core.services.osgiconfig.OSGIConfig;
import com.aem.sumit.core.services.osgiconfig.OSGiFactoryConfig;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = {SlingHttpServletRequest.class})
public class AemGeekServiceModel {

    private static final Logger LOG = LoggerFactory.getLogger(AemGeekServiceModel.class);

    @OSGiService
    AemGeekServiceA aemGeekServiceA;

    @OSGiService
    OSGIConfig osgiConfig;

    @OSGiService
    DemoOSGiFactory demoOSGiFactory;

    @PostConstruct
    private void init() {
        LOG.info("AemGeekServiceModel Initiated");
    }

    public Iterator<Page> getPages(){
        return aemGeekServiceA.getPages();
    }

    public String getServiceName(){ return osgiConfig.getServiceName(); }

    public int getServiceCount(){ return osgiConfig.getServiceCount(); }

    public boolean getLiveData(){ return osgiConfig.isLiveData(); }

    public String[] getCountries() { return osgiConfig.getCountries(); }

    public String getRunModes(){
        return osgiConfig.getRunModes(); }

    public List<DemoOSGiFactory> getAllConfig(){
        return demoOSGiFactory.getAllConfig();}
}
