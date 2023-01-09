package com.aem.sumit.core.solr.scheduler;

import com.aem.sumit.core.solr.helper.SolrSearchHelper;
import com.aem.sumit.core.solr.services.PageService;
import com.aem.sumit.core.solr.services.SolrSchedulerConfigService;
import com.aem.sumit.core.solr.services.SolrServiceAPI;
import com.aem.sumit.core.solr.services.SolrServiceManager;
import com.aem.sumit.core.utils.ResourceResolverUtil;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.Job;
import org.apache.sling.commons.scheduler.JobContext;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.xmlbeans.impl.common.ResolverUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component(service = Job.class, immediate = true)
public class SolrScheduler implements Job{

    private static final Logger LOG = LoggerFactory.getLogger(SolrScheduler.class);

    private int schedulerId;

    @Reference
    Scheduler scheduler;

    @Reference
    SolrServiceManager solrServiceManager;

    @Reference
    SolrServiceAPI solrServiceAPI;

    @Reference
    PageService pageService;

    @Reference
    SolrSchedulerConfigService solrSchedulerConfigService;

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Activate
    protected void activate() {addSchedulerJob();}

    private void addSchedulerJob() {
        String[] siteConfigs = solrSchedulerConfigService.getSiteConfigurations();
        for(String config: siteConfigs) {
            LOG.info("\n Configs - {} ",config);
            String[] conf = config.split("#");
            LOG.info("\n Configs - {} : {} ",conf[0],conf[1]);
            ScheduleOptions site = scheduler.EXPR(conf[1]);
            Map<String, Serializable> usMap = new HashMap<>();
            usMap.put("sitePath",conf[0]);
            site.config(usMap);
            scheduler.schedule(this,site);


        }
    }


    @Override
    public void execute(JobContext jobContext) {
        try {
            String sitePath = jobContext.getConfiguration().get("sitePath").toString();
            ResourceResolver resourceResolver = ResourceResolverUtil.newResolver(resourceResolverFactory);
            SolrSearchHelper solrSearchHelper = new SolrSearchHelper(sitePath,solrServiceManager,resourceResolver);
            LOG.info("\n ------SITE - {} : CORE - {} : SITEPATH - {}",solrSearchHelper.getSolrCAConfig().siteId(),solrSearchHelper.getSolrCAConfig().coreName(),sitePath);
            solrServiceAPI.addDocuments(pageService.getPagesDetail(sitePath),solrSearchHelper);
        }catch (Exception e) {
            LOG.error("\n Scheduler Error - {} ", e.getMessage());
        }
    }
}
