package com.aem.sumit.core.workflow;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.ContentFragmentException;
import com.adobe.cq.dam.cfm.FragmentTemplate;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.aem.sumit.core.constants.OpenAIConstants;
import com.aem.sumit.core.utils.ResourceResolverUtil;
import com.day.cq.dam.api.AssetManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.*;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Session;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component(
        service = WorkflowProcess.class,
        immediate = true,
        property = {
                "process.label" + " = Open AI Workflow Process",
                Constants.SERVICE_VENDOR + "= Open AI",
                Constants.SERVICE_DESCRIPTION + " = Custom step to create Content Fragment."
        }
)
public class OpenAIWorkflowProcess implements WorkflowProcess {

    @Reference
    ResourceResolverFactory resourceResolverFactory;


    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap processArguments) {


        log.info("\n EXECUTING WORKFLOW TO CREATE CONTENT FRAGMENT");
        try {
            ResourceResolver resourceResolver = ResourceResolverUtil.newResolver(resourceResolverFactory);
            WorkflowData workflowData = workItem.getWorkflowData();
            String cfPath = workflowData.getPayload().toString();
            String jcrPath = workflowData.getPayloadType();
            Resource cfResource = resourceResolver.resolve(cfPath);

            if(jcrPath.equalsIgnoreCase(OpenAIConstants.JCR_PATH) && Objects.nonNull(cfResource)) {
                ValueMap properties = cfResource.adaptTo(ValueMap.class);
                String aiImageUrl = properties.get("imagePath",String.class);
                String model = cfResource.getParent().getValueMap().get("cq:model", String.class);
                if (StringUtils.isNotBlank(model) && model.contains("open-ai-cf-model-version4") && Objects.nonNull(properties.get("title",String.class))) {
                   log.info("Creating Fragment for {} ",properties.get("title",String.class));
                   createNewFragment(properties,resourceResolver,aiImageUrl);
                }
                else {
                    //do nothing
                }

            }
        }catch (Exception e){

        }
    }

    private void createNewFragment(ValueMap properties, ResourceResolver resourceResolver, String aiImageUrl) throws Exception {
        String cfTitle = properties.get("title",String.class);
        String damImagePath = saveImageToDAM(aiImageUrl,resourceResolver,cfTitle);
        Resource templateOrModelRsc = resourceResolver.getResource("/conf/sumit/settings/dam/cfm/models/dxp-aem-demo");
        FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
        Resource parent = resourceResolver.resolve(OpenAIConstants.DAM_ROOT_AI);
        ContentFragment aiFragment = tpl.createFragment(parent, cfTitle.toLowerCase(), cfTitle);
        if (Objects.nonNull(aiFragment)) {
            aiFragment.getElements().forEachRemaining(contentElement -> {
                String name = contentElement.getName();
                String value = properties.get(name, String.class);
                if (value != null) {
                    switch (name) {
                        case "title":
                        case "keywords":
                            try {
                                contentElement.setContent(value, "text/plain");
                            } catch (ContentFragmentException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "imagePath":
                            try {
                                contentElement.setContent(damImagePath, "text/plain");
                            } catch (ContentFragmentException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "description":
                        case "summary":
                            try {
                                contentElement.setContent(value, "text/html");
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

    private String saveImageToDAM(String aiImagePath,ResourceResolver resourceResolver, String title) throws Exception {
        InputStream is = null;
        String imagePath = null;
        String mimeType = "";
        String imageName = title.toLowerCase().replace(" ", "-");
        try {

            // Open a connection to the remote image URL
            URL Url = new URL(aiImagePath);
            URLConnection uCon = Url.openConnection();
            is = uCon.getInputStream();
            mimeType = uCon.getContentType();

            // Create the asset in the DAM
            String fileExt = mimeType.replaceAll("image/", "");
            imagePath = "/content/dam/ai-generated" + "/" + imageName + "." + fileExt;
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
}
