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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.*;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Session;
import java.util.Objects;

@Slf4j
@Component(
        service = WorkflowProcess.class,
        immediate = true,
        property = {
                "process.label" + " = Open AI Workflow Process",
                Constants.SERVICE_VENDOR + "=Open AI",
                Constants.SERVICE_DESCRIPTION + " = Custom Open AI workflow step."
        }
)
public class OpenAIWorkflowProcess implements WorkflowProcess {

    @Reference
    ResourceResolverFactory resourceResolverFactory;


    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap processArguments) {


        log.info("\n ================================================================================= ");
        try {
            ResourceResolver resourceResolver = ResourceResolverUtil.newResolver(resourceResolverFactory);
            WorkflowData workflowData = workItem.getWorkflowData();
            String cfPath = workflowData.getPayload().toString();
            String jcrPath = workflowData.getPayloadType();
            Resource cfResource = resourceResolver.resolve(cfPath);
            Session session = workflowSession.adaptTo(Session.class);

            if(jcrPath.equalsIgnoreCase(OpenAIConstants.JCR_PATH) && Objects.nonNull(cfResource)) {
                ValueMap properties = cfResource.adaptTo(ValueMap.class);
                String model = cfResource.getParent().getValueMap().get("cq:model", String.class);
                if (StringUtils.isNotBlank(model) && model.contains("open-ai-cf-model-version2") && Objects.nonNull(properties.get("prompt",String.class))) {
                   log.info("Creating Fragment for {} ",properties.get("prompt",String.class));
                   createNewFragment(properties,resourceResolver,model);
                }
                else {
                    //do nothing
                }

            }
        }catch (Exception e){

        }
    }

    private void createNewFragment(ValueMap properties, ResourceResolver resourceResolver, String model) throws ContentFragmentException, PersistenceException {
        String cfTitle = Objects.nonNull(properties.get("prompt",String.class))?properties.get("prompt",String.class):"AI Generated CF";
        Resource templateOrModelRsc = resourceResolver.getResource(model);
        FragmentTemplate tpl = templateOrModelRsc.adaptTo(FragmentTemplate.class);
        Resource parent = resourceResolver.resolve(OpenAIConstants.DAM_ROOT_AI);
        ContentFragment aiFragment = tpl.createFragment(parent, cfTitle.toLowerCase(), cfTitle);
        if (Objects.nonNull(aiFragment)) {
            aiFragment.getElements().forEachRemaining(contentElement -> {
                String name = contentElement.getName();
                String value = properties.get(name, String.class);
                if (value != null) {
                    switch (name) {
                        case "prompt":
                            try {
                                contentElement.setContent(value, "text/plain");
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
}
