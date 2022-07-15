package com.aem.sumit.core.services.osgiconfig;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "AEM Geek Factory Config",description = "This config demonstrate OSGI Factory Configuration")
public @interface OSGiFactoryConfig {
    @AttributeDefinition(
            name = "Service ID",
            description = "Enter service id.",
            type = AttributeType.INTEGER)
    int serviceID();

    @AttributeDefinition(
            name = "Service Name",
            description = "Enter service name.",
            type = AttributeType.STRING)
    public String serviceName() default "Service #";

    @AttributeDefinition(
            name = "Service URL",
            description = "Add Service URL.",
            type = AttributeType.STRING
    )
    String serviceURL() default "URL #";
}
