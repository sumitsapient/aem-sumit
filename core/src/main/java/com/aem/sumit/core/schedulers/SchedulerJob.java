package com.aem.sumit.core.schedulers;

import com.aem.sumit.core.schedulers.config.SchedulerConfiguration;
import org.apache.sling.commons.scheduler.Job;
import org.apache.sling.commons.scheduler.JobContext;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component(service = Job.class,immediate = true)
@Designate(ocd = SchedulerConfiguration.class)
public class SchedulerJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerJob.class);

    private int schedulerId;

    @Reference
    Scheduler scheduler;

    @Activate
    protected void activate(SchedulerConfiguration configuration)
    {
        schedulerId = configuration.schedulerName().hashCode();
        addScheduler(configuration);

    }

    private void addScheduler(SchedulerConfiguration configuration) {

        ScheduleOptions in = scheduler.EXPR("0 34 16 1/1 * ? *");
        Map<String, Serializable> inMap = new HashMap<>();
        inMap.put("country","IN");
        inMap.put("url","www.in.com");
        in.config(inMap);
        scheduler.schedule(this,in);

        ScheduleOptions de = scheduler.EXPR("0 37 16 1/1 * ? *");
        Map<String, Serializable> deMap = new HashMap<>();
        deMap.put("country","DE");
        deMap.put("url","www.de.com");
        de.config(deMap);
        scheduler.schedule(this,de);

        ScheduleOptions us = scheduler.EXPR("0 39 16 1/1 * ? *");
        Map<String, Serializable> usMap = new HashMap<>();
        usMap.put("country","US");
        usMap.put("url","www.us.com");
        us.config(usMap);
        scheduler.schedule(this,us);

    }

    @Deactivate
    protected void deactivate(SchedulerConfiguration configuration) {
        scheduler.unschedule(String.valueOf(schedulerId));
    }

    @Override
    public void execute(JobContext jobContext) {
        LOG.info("Executing Job",jobContext.getConfiguration().get("country"),jobContext.getConfiguration().get("url"));
    }
}
