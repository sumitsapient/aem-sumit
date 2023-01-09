package com.aem.sumit.core.solr.configs;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Solr OSGI Configuration",description = "Factory Config to Solr OSGI configs")
public @interface SolrOSGIConfig {

    @AttributeDefinition(name = "Site Id")
    String siteId();

    @AttributeDefinition(name = "Solr Endpoint")
    String solrServerEndpoint();

    @AttributeDefinition(name = "Solr Username")
    String solrUsername();

    @AttributeDefinition(name = "Solr Password")
    String solrPassword();

    @AttributeDefinition(name = "Solr Response")
    String[] solrResponse();
}
