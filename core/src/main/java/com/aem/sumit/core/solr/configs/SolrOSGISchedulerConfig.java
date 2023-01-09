package com.aem.sumit.core.solr.configs;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "OSGi Solr Scheduler Configuration", description = "Solr Scheduler Configuration")
public @interface SolrOSGISchedulerConfig {

    @AttributeDefinition(name = "Site Configuration")
    String[] siteConfiguration();
}
