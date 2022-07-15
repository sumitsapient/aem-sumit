package com.aem.sumit.core.listeners;

import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;

@Component(immediate = true,service = EventListener.class)
public class JCRHandler implements EventListener {

    private static final Logger LOG = LoggerFactory.getLogger(JCRHandler.class);
    private Session session;

    @Reference
    SlingRepository slingRepository;

    @Activate
    public void activate() {
        try {
            session = slingRepository.loginService("sumitserviceuser",null);
            String[] cqPageNodetype={"cq:PageContent"};
            session.getWorkspace().getObservationManager().addEventListener(
                    this,                                                          //this -> refers to onEvent()
                    Event.NODE_ADDED | Event.PROPERTY_ADDED,                               // int code for type of event
                    "/content/sumit/us/en",                                                // path
                    true,                                                                 // is Deep? -> true will search all child
                    null,                                                             //UUID's filter
                    null,  //  cqPageNodetype ->  if we want to listen to cq:PageContent only   // nodetypes filter -> string array to restrict node type
                    false);
        } catch (RepositoryException e) {
            LOG.info("JCRHandler Listener Exception {}",e.getMessage());
        }

    }


    public void onEvent(EventIterator eventIterator) {

        while(eventIterator.hasNext() && !eventIterator.hasNext()) {

            try{
                if(eventIterator.nextEvent()!=null) {
                    //Logic goes here
                    LOG.info("\n Type : {}, Path : {}",eventIterator.nextEvent().getType(),eventIterator.nextEvent().getPath());
                }
            }
            catch (RepositoryException e) {
                e.printStackTrace();
            }


        }


    }
}
