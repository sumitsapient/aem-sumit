package com.aem.sumit.core.schedulers;

import com.aem.sumit.core.schedulers.config.SchedulerConfiguration;
import org.apache.sling.commons.scheduler.Job;
import org.apache.sling.commons.scheduler.JobContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = Job.class,immediate = true)
@Designate(ocd = SchedulerConfiguration.class)
public class SchedulerJobTask implements Job {

    //Create Scheduler SchedulerJob.java more future ready.
    //In order to add oe more country, create factory config

    @Override
    public void execute(JobContext jobContext) {

    }
}
