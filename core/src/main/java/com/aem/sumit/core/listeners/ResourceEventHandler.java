package com.aem.sumit.core.listeners;
/*
      a. are registered with Framework service registry(i. like OSGi service) and are notified with an ResourceChange Object.
	  b. can inspect the received ResourceChange objects to determine the type of change,location and other properties.
	  c. ResourceChange must be registered with a service property PATHS whose value is list of resource paths .
	  d. must be registered with service property CHANGES whose value is list of event type.
* */

import com.aem.sumit.core.utils.ResourceResolverUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChangeListener;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import java.util.List;

@Component(
        immediate = true,
        service = ResourceChangeListener.class,
        property = {
                ResourceChangeListener.PATHS+"=/content/sumit/us/en/faqs",
                ResourceChangeListener.CHANGES+"=ADDED",
                ResourceChangeListener.CHANGES+"=REMOVED",
                ResourceChangeListener.CHANGES+"=CHANGED"
        }
)
public class ResourceEventHandler implements ResourceChangeListener{

    private static final Logger LOG = LoggerFactory.getLogger(ResourceEventHandler.class);
    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public void onChange(List<ResourceChange> list) {
        for(ResourceChange rc : list){
            try {
                LOG.info("\n Event : {} , Resource : {} ", rc.getType(), rc.getPath());
                ResourceResolver resourceResolver= ResourceResolverUtil.newResolver(resourceResolverFactory);
                Resource resource=resourceResolver.getResource(rc.getPath());
                Node node=resource.adaptTo(Node.class);
                node.setProperty("eventhandlertask","Event "+rc.getType()+" by "+resourceResolver.getUserID());
                resourceResolver.commit();
            }catch (Exception e){
                LOG.info("\n Exception : {} ", e.getMessage());
            }
        }

    }
}
