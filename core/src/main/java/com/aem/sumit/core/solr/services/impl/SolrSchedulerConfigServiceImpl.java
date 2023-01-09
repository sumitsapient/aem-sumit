package com.aem.sumit.core.solr.services.impl;

import com.aem.sumit.core.solr.configs.SolrOSGISchedulerConfig;
import com.aem.sumit.core.solr.services.SolrSchedulerConfigService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = SolrSchedulerConfigService.class,immediate = true)
@Designate(ocd = SolrOSGISchedulerConfig.class)
public class SolrSchedulerConfigServiceImpl implements SolrSchedulerConfigService {

    private String[] siteConfigurations;

    @Activate
    protected void activate(final SolrOSGISchedulerConfig config) {
        siteConfigurations = config.siteConfiguration();
    }
    @Override
    public String[] getSiteConfigurations() {
        return siteConfigurations;
    }
}
