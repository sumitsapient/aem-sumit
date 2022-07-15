package com.aem.sumit.core.schedulers;

import com.aem.sumit.core.schedulers.config.SchedulerConfiguration;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Runnable.class,immediate = true)
@Designate(ocd = SchedulerConfiguration.class)
public class SchedulerRunnable implements Runnable{

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerRunnable.class);

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
        ScheduleOptions options = scheduler.EXPR(configuration.cronExpression());
        options.name(String.valueOf(schedulerId));
        options.canRunConcurrently(false);
        scheduler.schedule(this,options);
        LOG.info("SCHEDULER ADDED");
    }

    @Deactivate
    protected void deactivate(SchedulerConfiguration configuration) {
        scheduler.unschedule(String.valueOf(schedulerId));
    }

    @Override
    public void run() {
      LOG.info("RUN METHOD EXECUTING");
    }
}
