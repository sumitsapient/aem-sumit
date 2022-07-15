package com.aem.sumit.core.services.osgiconfig;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

@ObjectClassDefinition(name="AEM Geek OSGi Configuration",
        description = "Modular OSGi Configuration demo.")
public @interface GeeksOSGiConfig {

    @AttributeDefinition(
            name = "Service Name",
            description = "Enter service name.",
            type = AttributeType.STRING)
    public String serviceName() default "AEM Geeks Service";

    @AttributeDefinition(
            name = "Service Count",
            description = "Add Service Count.",
            type = AttributeType.INTEGER
    )
    int serviceCount() default 5 ;

    @AttributeDefinition(
            name = "Live Data",
            description = "Enable Live Data",
            type = AttributeType.BOOLEAN
    )
    boolean liveData() default false ;

    @AttributeDefinition(
            name = "Countries",
            description = "Add country locale",
            type = AttributeType.STRING
    )
    String[] countries() default {"en","in"} ;

    @AttributeDefinition(
            name = "Run Mode",
            description = "Select run mode",
            options = {
                    @Option(label = "Author",value = "author"),
                    @Option(label = "Publish",value = "publish"),
                    @Option(label = "Both",value = "both")
            },
            type = AttributeType.STRING
    )
    String runModes() default "author" ;


}
