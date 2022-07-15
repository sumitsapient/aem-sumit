package com.aem.sumit.core.schedulers.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "AEM Geek Scheduler",description = "AEM Geek Scheduler Configuration")
public @interface SchedulerConfiguration {

    @AttributeDefinition(name = "Scheduler Name",
    description = "Name of the scheduler",
    type = AttributeType.STRING)
    public String schedulerName() default "Custom Sling Default Configuration";

    @AttributeDefinition(name = "Cron Expression",
            description = "Configure Cron",
            type = AttributeType.STRING)
    public String cronExpression() default "0 0 12 1/1 * ? *"; //Every Day 12:pm
}
