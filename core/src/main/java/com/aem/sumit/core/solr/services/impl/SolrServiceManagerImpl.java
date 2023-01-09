package com.aem.sumit.core.solr.services.impl;

import com.aem.sumit.core.solr.services.SolrOSGIConfigService;
import com.aem.sumit.core.solr.services.SolrServiceManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component(service = SolrServiceManager.class, immediate = true)
public class SolrServiceManagerImpl implements SolrServiceManager {

    private static final Logger LOG = LoggerFactory.getLogger(SolrServiceManagerImpl.class);

    private Map<String,SolrOSGIConfigService> configServiceMap;


    /**
     * Executed on Configuration Add Event
     * @param config New configuration for factory*/
    @Reference(service = SolrOSGIConfigService.class, cardinality = ReferenceCardinality.MULTIPLE,policy = ReferencePolicy.DYNAMIC)
    protected synchronized void bindApiServiceConfiguration(final SolrOSGIConfigService config) {
        LOG.info("\n ====bindApiServiceConfiguration: "+ config.getSiteId());
        if(configServiceMap == null) {
            configServiceMap = new ConcurrentHashMap<>();
        }
        configServiceMap.put(config.getSiteId(),config);

    }

    /**
     * Executed on Configuration Remove Event
     * @param config New configuration for factory*/
    protected synchronized void unbindApiServiceConfiguration(final SolrOSGIConfigService config) {
        LOG.info("\n ====unbindApiServiceConfiguration: "+ config.getSiteId());
        configServiceMap.remove(config.getSiteId());
    }

    @Override
    public SolrOSGIConfigService getServiceConfiguration(String siteId) {
        return configServiceMap.get(siteId);
    }
}
