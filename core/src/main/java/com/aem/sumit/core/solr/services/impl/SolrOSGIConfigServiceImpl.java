package com.aem.sumit.core.solr.services.impl;

import com.aem.sumit.core.solr.configs.SolrOSGIConfig;
import com.aem.sumit.core.solr.services.SolrOSGIConfigService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = SolrOSGIConfigService.class, immediate = true)
@Designate(ocd = SolrOSGIConfig.class , factory = true)
public class SolrOSGIConfigServiceImpl implements SolrOSGIConfigService {

    private String siteId;

    private String solrServerEndpoint;

    private String solrUsername;

    private String solrPassword;

    private String[] solrResponse;

    @Activate
    @Modified
    protected void activate(final SolrOSGIConfig config) {
     this.siteId = config.siteId();
     this.solrServerEndpoint = config.solrServerEndpoint();
     this.solrUsername = config.solrUsername();
     this.solrPassword = config.solrPassword();
     this.solrResponse = config.solrResponse();
    }

    public String getSiteId() {
        return siteId;
    }

    public String getSolrServerEndpoint() {
        return solrServerEndpoint;
    }

    public String getSolrUsername() {
        return solrUsername;
    }

    public String getSolrPassword() {
        return solrPassword;
    }

    public String[] getSolrResponse() {
        return solrResponse;
    }
}
