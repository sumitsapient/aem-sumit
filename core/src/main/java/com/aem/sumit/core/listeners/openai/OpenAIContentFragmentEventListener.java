package com.aem.sumit.core.listeners.openai;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.ContentFragmentException;
import com.adobe.cq.dam.cfm.FragmentTemplate;
import com.aem.sumit.core.constants.OpenAIConstants;
import com.aem.sumit.core.utils.ResourceResolverUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.*;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component(service = EventListener.class, immediate = true)
public class OpenAIContentFragmentEventListener implements EventListener {

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Reference
    JobManager jobManager;
    private Session session;

    private ResourceResolver resourceResolver;

    @Activate
    public void activate(ComponentContext context) throws Exception {
        resourceResolver = ResourceResolverUtil.newResolver(resourceResolverFactory);
        session = resourceResolver.adaptTo(Session.class);
        session.getWorkspace().getObservationManager().addEventListener(this,
                      Event.PROPERTY_ADDED |
                        Event.PROPERTY_CHANGED |
                        Event.NODE_ADDED ,OpenAIConstants.DAM_ROOT_SUMIT, true,null, null, false);
    }

    @Override
    public void onEvent(EventIterator events) {
        List<String> masterProps = new ArrayList();
        while (events.hasNext()) {
            final Event event = events.nextEvent();
            try {
                if(event.getPath().contains(OpenAIConstants.JCR_CF_MASTER) && !event.getPath().contains("@")) {
                log.info("\n Property Changed {}", event.getPath());
                masterProps.add(event.getPath());
                }
            } catch (RepositoryException e) {
                log.error("Error occurred in event listener {}",e);
            }
        }
        if(masterProps.size() > 0) {
            Map<String, Object> jobProperties = new HashMap<String, Object>();
            jobProperties.put("event", "CHANGED");
            jobProperties.put("path", masterProps.get(0));
            Job job = jobManager.addJob("openai/job",jobProperties);
            String masterPath = getMasterPath(masterProps.get(0));
            List<String> updatedCFElement = masterProps.stream()
                    .map(s -> s.substring(s.lastIndexOf('/') + 1))
                    .collect(Collectors.toList());
            getNewFragmentElements(masterPath,updatedCFElement);
        }
    }

    private void getNewFragmentElements( String masterPath, List<String> updatedCFElement)  {
        try {

            Resource masterResource = resourceResolver.resolve(masterPath);
            if(Objects.nonNull(masterResource)) {
                ValueMap properties = masterResource.adaptTo(ValueMap.class);
                String model = masterResource.getParent().getValueMap().get("cq:model", String.class);
                if (StringUtils.isNotBlank(model) && model.contains("open-ai-cf-model-version2") && Objects.nonNull(properties.get("prompt",String.class))) {
                    log.info("Creating Fragment for {} ",properties.get("prompt",String.class));
                    createNewFragment(properties,resourceResolver,model,updatedCFElement);
                }
                else {
                    //do nothing
                }

            }

        } catch (ContentFragmentException | PersistenceException e) {
            log.error("Error occured while extracting content fragment elements {}",e);
        }
    }

    private String getMasterPath(String element) {
        String masterPath = null;
        int lastIndex = element.lastIndexOf('/');
         masterPath = element.substring(0,lastIndex);
         return masterPath;
    }

    private void createNewFragment(ValueMap properties, ResourceResolver resourceResolver, String model, List<String> updatedFields) throws ContentFragmentException, PersistenceException {
        String cfTitle = Objects.nonNull(properties.get("prompt",String.class))?properties.get("prompt",String.class):"AI Generated CF";
        Resource templateOrModelRsc = resourceResolver.getResource(model);
        FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
        Resource parent = resourceResolver.resolve(OpenAIConstants.DAM_ROOT_AI);
        ContentFragment aiFragment = tpl.createFragment(parent, cfTitle.toLowerCase(), cfTitle);
        if (Objects.nonNull(aiFragment)) {
            updatedFields.forEach(field->{
                String value = properties.get(field, String.class);
                if (value != null) {
                    switch (field) {
                        case "prompt":
                            try {
                                aiFragment.getElement(field).setContent(value, "text/plain");
                            } catch (ContentFragmentException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "shortWarmthSummary":
                        case "shortAggressiveSummary":
                        case "shortFormalSummary":
                        case "detailedWarmthSummary":
                        case "detailedAggressiveSummary":
                        case "detailedFormalSummary":
                            try {
                                aiFragment.getElement(field).setContent(value, "text/html");
                            } catch (ContentFragmentException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        default:
                            break;
                    }
                }
            });
            resourceResolver.commit();
        }



    }

    @Deactivate
    protected void deactivate() {
        if(session != null) {
            session.logout();
        }
    }
}
