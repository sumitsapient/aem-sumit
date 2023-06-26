package com.aem.sumit.core.servlets.openai;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.aem.sumit.core.services.openai.APIInvoker;
import com.drew.lang.annotations.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.day.cq.wcm.api.Page;

@Slf4j
@Component(
        immediate = true,
        service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=ChatGPT Teaser Integration",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + TeaserPromoServlet.RESOURCE_PATH
        }
)
public class TeaserPromoServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;
    static final String RESOURCE_PATH = "/bin/teaserPromoServlet";
    static final String PAGE_PATH = "pagePath";
    static final String TITLE_TOKEN = "titleToken";
    static final String DESC_TOKEN = "descToken";
    private static final String TEASER_TITLE = "jcr:title";
    private static final String TEASER_DESCRIPTION = "jcr:description";


    @Reference
    private APIInvoker apiInvoker;

    @Override
    protected void doGet(@NotNull SlingHttpServletRequest request, @NotNull SlingHttpServletResponse response) throws ServletException, IOException {
        String pagePath = request.getParameter(PAGE_PATH);
        String titleTokenParam = request.getParameter(TITLE_TOKEN);
        String descTokenParam = request.getParameter(DESC_TOKEN);
        int titleToken = 0;
        int descToken = 0;
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isNoneEmpty(pagePath)) {
            Resource pageResource = request.getResourceResolver().resolve(pagePath);
            if(!ResourceUtil.isNonExistingResource(pageResource)) {
                Page page = pageResource.adaptTo(Page.class);
                String teaserTitle = page.getProperties().get(TEASER_TITLE, StringUtils.EMPTY);
                String teaserDescription = page.getProperties().get(TEASER_DESCRIPTION, StringUtils.EMPTY);
                try {
                    if(StringUtils.isNoneEmpty(titleTokenParam) && StringUtils.isNotEmpty(descTokenParam)) {
                        titleToken = Integer.parseInt(titleTokenParam);
                        descToken = Integer.parseInt(descTokenParam);
                    }
                    jsonObject.put(TEASER_TITLE, apiInvoker.invokeAPI(teaserTitle, titleToken));
                    jsonObject.put(TEASER_DESCRIPTION, apiInvoker.invokeAPI(teaserDescription, descToken));
                } catch (JSONException e) {
                    log.error(e.getMessage());
                }
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().print(jsonObject);
            }
        }
    }
}