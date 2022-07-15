package com.aem.sumit.core.listeners;

/*
      a. are registered with Framework service registry(i. like OSGi service) and are notified with an Event Object.
      b. can inspect the received event object to determine its topic and properties.
      c. event objects must be registered with a service property EventConstants.EVENT_TOPIC whose values are list of topics
         for which the event handler listen to.
      d. can also be registered with EventConstants.EVENT_FILTER service property to further filte the events.
 */

import com.aem.sumit.core.utils.ResourceResolverUtil;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;

@Component(service = EventHandler.class,
        immediate = true,
        property = {
                EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/ADDED",
                EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/CHANGED",
                EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/REMOVED",
                EventConstants.EVENT_FILTER +"=(path=/content/sumit/us/en/faqs/*)"
        })
public class OSGiEventHandler implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(OSGiEventHandler.class);

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    public void handleEvent(final Event event) {
        LOG.info("\n Resource event: {} at: {}", event.getTopic(), event.getProperty(SlingConstants.PROPERTY_PATH));
        try {
            ResourceResolver resourceResolver= ResourceResolverUtil.newResolver(resourceResolverFactory);
            Resource resource=resourceResolver.getResource(event.getProperty(SlingConstants.PROPERTY_PATH).toString());
            Node node=resource.adaptTo(Node.class);
            node.setProperty("eventhandlertask","Event "+event.getTopic()+" by "+resourceResolver.getUserID());
            //resourceResolver.commit();
            for(String prop : event.getPropertyNames()){
                LOG.info("\n Property : {} , Value : {} ", prop,event.getProperty(prop));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

