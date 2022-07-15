package com.aem.sumit.core.services.contextaware;

import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

@Configuration(label = "Geeks- Context Aware Config",description = "CA for AEm Geeks")
public @interface GeeksCAConfig {

    @Property(label = "country",description = "country name")
    String siteCountry() default "us";

    @Property(label = "locale",description = "locale name")
    String siteLocale() default "en";

    @Property(label = "admin",description = "admin name")
    String siteAdmin() default "aem-geek";

    @Property(label = "section",description = "section name")
    String siteSection() default "aem";
}
