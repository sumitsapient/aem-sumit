package com.aem.sumit.core.solr.configs;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

@Configuration(name = "Solr Context Aware Config",collection = true, description = "Context Aware Configuration For AEM and Solr Integration")
public @interface SolrCAConfig {

    @Property(label = "SiteId", description = "SiteId for specific site")
    String siteId() default "sumit-learning";

    @Property(label = "Solr Core",description = "Solr Core corresponding to SiteId" )
    String coreName() default "wknd";

    @Property(label = "Field Names",description = "Field names to include in response")
    String[] fieldNames() default {StringUtils.EMPTY};

}
