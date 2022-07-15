package com.aem.sumit.core.services.osgiconfig;

import com.aem.sumit.core.services.AemGeekServiceAImpl;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Component(service = DemoOSGiFactory.class,immediate = true)
@Designate(ocd = OSGiFactoryConfig.class,factory = true)
public class DemoOSGiFactoryImpl implements DemoOSGiFactory{

   private int ID;
   private String name;
   private String url;
   private List<DemoOSGiFactory> factoryConfigsList;
   private static final Logger LOG = LoggerFactory.getLogger(DemoOSGiFactoryImpl.class);


   @Activate
   protected void activate(OSGiFactoryConfig config) {
       LOG.info("DemoOSGiFactoryImpl Activated");
       ID = config.serviceID();
       name = config.serviceName();
       url = config.serviceURL();
       LOG.info("DemoOSGiFactoryImpl Values {} {} {}",ID,name,url);
   }

   @Reference(service = DemoOSGiFactory.class,cardinality = ReferenceCardinality.MULTIPLE,policy = ReferencePolicy.DYNAMIC)
   public void bindOSGIFactoryConfig( final DemoOSGiFactory osGiFactoryConfig) {
       LOG.info("Inside Bind",factoryConfigsList);
       if(factoryConfigsList == null)
       {
           factoryConfigsList = new ArrayList<>();
       }

       factoryConfigsList.add(osGiFactoryConfig);
   }

   public void unbindOSGIFactoryConfig( final DemoOSGiFactory osGiFactoryConfig) {
       LOG.info("Unbinding");
       factoryConfigsList.remove(osGiFactoryConfig);
   }

    @Override
    public String getServiceName() {
        return name;
    }

    @Override
    public String getServiceURL() {
        return url;
    }

    @Override
    public int getServiceID() {
        return ID;
    }

    @Override
    public List<DemoOSGiFactory> getAllConfig() {
        return factoryConfigsList;
    }
}
