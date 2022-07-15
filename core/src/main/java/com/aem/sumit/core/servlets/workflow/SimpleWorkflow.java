package com.aem.sumit.core.servlets.workflow;


import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletPaths(value = {"/bin/executeworkflow"})
public class SimpleWorkflow extends SlingSafeMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleWorkflow.class);

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        //http://localhost:4502/bin/executeworkflow?page=/content/sumit/us/en/adventures
        String status = "Workflow Executing";
        final ResourceResolver resourceResolver = request.getResourceResolver();
        String payload = request.getRequestParameter("page").getString();
        try {
            if(StringUtils.isNotBlank(payload)) {
                WorkflowSession workflowSession = resourceResolver.adaptTo(WorkflowSession.class);
                WorkflowModel workflowModel = workflowSession.getModel("/var/workflow/models/page-version");
                WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH",payload);
                status = workflowSession.startWorkflow(workflowModel,workflowData).getState();

            }
        } catch (Exception e) {
            LOG.error("Error in Workflow {}",e.getMessage());
        }
        response.setContentType("application/json");
        response.getWriter().write(status);
    }
}
