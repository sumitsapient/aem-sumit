package com.aem.sumit.core.listeners.openai;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.ContentFragmentException;
import com.adobe.cq.dam.cfm.FragmentTemplate;
import com.aem.sumit.core.constants.OpenAIConstants;
import com.aem.sumit.core.utils.ResourceResolverUtil;
import com.day.cq.dam.api.AssetManager;
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
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component(service = EventListener.class, immediate = true)
public class OpenAIContentFragmentEventListenerV2 implements EventListener {

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
                        Event.NODE_ADDED ,"/content/dam/sumit/yadav", true,null, null, false);
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
                String aiImageUrl = properties.get("imagePath",String.class);
                String model = masterResource.getParent().getValueMap().get("cq:model", String.class);
                if (StringUtils.isNotBlank(model) && model.contains("open-ai-cf-model-version4") && Objects.nonNull(properties.get("title",String.class)) ) {
                    log.info("Creating Fragment for {} ",properties.get("title",String.class));
                    createNewFragment(properties,resourceResolver,updatedCFElement,aiImageUrl);
                }
                else {
                    //do nothing
                }

            }

        } catch (Exception e) {
            log.error("Error occured while extracting content fragment elements {}",e);
        }
    }

    private String getMasterPath(String element) {
        String masterPath = null;
        int lastIndex = element.lastIndexOf('/');
         masterPath = element.substring(0,lastIndex);
         return masterPath;
    }

    private void createNewFragment(ValueMap properties, ResourceResolver resourceResolver,List<String> updatedFields,String aiImageUrl) throws Exception {
        String damImagePath = saveImageToDAM(aiImageUrl,resourceResolver);
        String cfTitle = properties.get("title",String.class);
        Resource templateOrModelRsc = resourceResolver.getResource("/conf/sumit/settings/dam/cfm/models/dxp-aem-demo");
        FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
        Resource parent = resourceResolver.resolve(OpenAIConstants.DAM_ROOT_AI);
        ContentFragment aiFragment = tpl.createFragment(parent, cfTitle.toLowerCase(), cfTitle);
        if (Objects.nonNull(aiFragment)) {
            updatedFields.forEach(field->{
                String value = properties.get(field, String.class);
                if (value != null) {
                    switch (field) {
                        case "title":
                        case "imagePath":
                        case "keywords":
                            try {
                                aiFragment.getElement(field).setContent(value, "text/plain");
                            } catch (ContentFragmentException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "description":
                        case "summary":
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

    private String saveImageToDAM(String aiImagePath,ResourceResolver resourceResolver) throws Exception {
        InputStream is = null;
        String imagePath = null;
        String mimeType = "";
        try {

            // Open a connection to the remote image URL
            URL Url = new URL(aiImagePath);
            URLConnection uCon = Url.openConnection();
            is = uCon.getInputStream();
            mimeType = uCon.getContentType();

            // Create the asset in the DAM
            String fileExt = mimeType.replaceAll("image/", "");
            imagePath = "/content/dam/ai-generated" + "/" + "ai" + "." + fileExt;
            resourceResolver.adaptTo(AssetManager.class).createAsset(imagePath, is, mimeType, true);
            //updateImageProperties(resourceResolver, imagePath, inputData.getComponentpath());

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            // Close the InputStream
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {

            }
        }
        return imagePath;
    }

    @Deactivate
    protected void deactivate() {
        if(session != null) {
            session.logout();
        }
    }
}
