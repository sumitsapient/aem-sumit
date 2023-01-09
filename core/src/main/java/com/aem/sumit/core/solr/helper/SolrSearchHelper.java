package com.aem.sumit.core.solr.helper;

import com.aem.sumit.core.solr.configs.SolrCAConfig;
import com.aem.sumit.core.solr.services.PageService;
import com.aem.sumit.core.solr.services.SolrOSGIConfigService;
import com.aem.sumit.core.solr.services.SolrServiceAPI;
import com.aem.sumit.core.solr.services.SolrServiceManager;
import com.aem.sumit.core.solr.util.CAUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SolrSearchHelper {

    private static final Logger LOG = LoggerFactory.getLogger(SolrSearchHelper.class);

    private SolrOSGIConfigService solrOSGIConfigService;

    private SlingHttpServletRequest request;

    private SlingHttpServletResponse response;

    private SolrServiceManager solrServiceManager;

    private SolrCAConfig solrCAConfig;

    private String sitePath;

    public SolrSearchHelper(SlingHttpServletRequest request, SlingHttpServletResponse response, SolrServiceManager solrServiceManager) {

        this.request = request;
        this.response = response;
        this.solrServiceManager = solrServiceManager;
        this.sitePath = request.getParameter("sitePath");
        this.solrCAConfig = CAUtils.getContextAwareConfig(sitePath,request.getResourceResolver(),SolrCAConfig.class);

    }

    public SolrSearchHelper(String sitePath, SolrServiceManager solrServiceManager , ResourceResolver resourceResolver) {

        this.solrServiceManager = solrServiceManager;
        this.sitePath = sitePath;
        this.solrCAConfig = CAUtils.getContextAwareConfig(sitePath,resourceResolver,SolrCAConfig.class);

    }

    public void searchSolr(PageService pageService, SolrServiceAPI solrServiceAPI) {
        JSONArray pagesArray = new JSONArray();

        try{
            final ResourceResolver resourceResolver = this.request.getResourceResolver();
            solrOSGIConfigService = solrServiceManager.getServiceConfiguration(solrCAConfig.siteId());
            LOG.info("\n Site ID- {} : Core- {}",solrOSGIConfigService.getSiteId(),solrOSGIConfigService.getSolrServerEndpoint());
            String searchOperation = request.getParameter("searchParameter");
            LOG.info("\n Operation-{}",searchOperation);
            if(StringUtils.containsIgnoreCase(searchOperation,"index")) {
                List<PageDetail> pageDetails = pageService.getPagesDetail(sitePath);
                String documentAdded = solrServiceAPI.addDocuments(pageDetails,this);
                response.getWriter().write(documentAdded);
            } else if (StringUtils.containsIgnoreCase(searchOperation,"index")) {
                //TODO- Delete
                response.getWriter().write("TO DO FOR DELETE");
            } else {
                response.getWriter().write("Choose operation for Solr");
            }

        }
        catch (Exception e) {
            LOG.error("\n Error while indexing  {}", e.getMessage());
        }
    }

    public SolrOSGIConfigService getSolrOSGIConfigService() {
        return solrOSGIConfigService;
    }

    public SolrCAConfig getSolrCAConfig() {
        return solrCAConfig;
    }
}
