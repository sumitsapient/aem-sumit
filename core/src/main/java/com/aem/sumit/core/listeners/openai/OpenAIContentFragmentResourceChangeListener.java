package com.aem.sumit.core.listeners.openai;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;
import com.aem.sumit.core.utils.ResourceResolverUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChangeListener;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;
import java.util.Objects;
//This Listener triggers a workflow for any change under /content/dam/sumit
//The workflow creates a new content fragment with changed resource.
@Slf4j
@Component(service = { ResourceChangeListener.class}, immediate = true, property = {
        Constants.SERVICE_DESCRIPTION + "=Initiate Approval Workflow for OpenAI CF. ",
        ResourceChangeListener.CHANGES + "=CHANGED", ResourceChangeListener.CHANGES + "=REMOVED",
        ResourceChangeListener.CHANGES + "=ADDED"
       // ResourceChangeListener.PATHS + "=" + OpenAIConstants.DAM_ROOT_SUMIT
        })
public class OpenAIContentFragmentResourceChangeListener implements ResourceChangeListener {
    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public void onChange(List<ResourceChange> changes) {
        for (final ResourceChange change : changes) {
            String path = change.getPath();
            log.info(change.getType() + " event received for : " + path);
            if(path.contains("/jcr:content/data/master")) {
                ResourceResolver resourceResolver = null;
                try {
                    resourceResolver = ResourceResolverUtil.newResolver(resourceResolverFactory);
                    final WorkflowSession workflowSession = resourceResolver.adaptTo(WorkflowSession.class);
                    final String model = "/var/workflow/models/openai";
                    final WorkflowModel workflowModel = Objects.requireNonNull(workflowSession).getModel(model);
                    final WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH", path);
                    workflowSession.startWorkflow(workflowModel, workflowData);
                    log.info("Workflow: {} started", model);
                    resourceResolver.close();

                } catch (LoginException | WorkflowException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
