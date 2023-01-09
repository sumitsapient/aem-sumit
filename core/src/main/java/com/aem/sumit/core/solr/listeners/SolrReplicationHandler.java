package com.aem.sumit.core.solr.listeners;

import com.aem.sumit.core.solr.helper.SolrSearchHelper;
import com.aem.sumit.core.solr.services.PageService;
import com.aem.sumit.core.solr.services.SolrServiceAPI;
import com.aem.sumit.core.solr.services.SolrServiceManager;
import com.aem.sumit.core.utils.ResourceResolverUtil;
import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationEvent;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = EventHandler.class,immediate = true,property = {EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC})
public class SolrReplicationHandler implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SolrReplicationHandler.class);

    @Reference
    SolrServiceManager solrServiceManager;

    @Reference
    SolrServiceAPI solrServiceAPI;

    @Reference
    PageService pageService;

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public void handleEvent(final Event event) {

        try {
            String pagePath = ReplicationAction.fromEvent(event).getPath();
            ResourceResolver resourceResolver = ResourceResolverUtil.newResolver(resourceResolverFactory);
            SolrSearchHelper solrSearchHelper = new SolrSearchHelper(pagePath,solrServiceManager,resourceResolver);
            if(ReplicationAction.fromEvent(event).getType().equals(ReplicationActionType.ACTIVATE)) {
                LOG.info("\n Activate : {}", ReplicationAction.fromEvent(event).getPath());
                solrServiceAPI.indexPage(solrSearchHelper,pageService.getSinglePageDetail(pagePath));
            }
            if(ReplicationAction.fromEvent(event).getType().equals(ReplicationActionType.DEACTIVATE)) {
                LOG.info("\n Deactivate : {}", ReplicationAction.fromEvent(event).getPath());
                solrServiceAPI.deletePage(solrSearchHelper,pageService.getSinglePageDetail(pagePath));
            }
        }
        catch (Exception e) {LOG.error("\n Page was not able to index {}",e.getMessage());}

    }
}
