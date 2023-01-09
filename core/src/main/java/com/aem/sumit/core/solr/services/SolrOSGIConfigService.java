package com.aem.sumit.core.solr.services;

public interface SolrOSGIConfigService {

    public String getSiteId();

    public  String getSolrServerEndpoint();

    public String getSolrUsername();

    public String getSolrPassword();

    public String[] getSolrResponse();
}
