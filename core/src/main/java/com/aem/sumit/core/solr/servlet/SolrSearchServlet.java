package com.aem.sumit.core.solr.servlet;


import com.aem.sumit.core.solr.helper.SolrSearchHelper;
import com.aem.sumit.core.solr.services.PageService;
import com.aem.sumit.core.solr.services.SolrServiceAPI;
import com.aem.sumit.core.solr.services.SolrServiceManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletPaths(value = {"/bin/solrsearch"})
public class SolrSearchServlet extends SlingAllMethodsServlet {

    // http://localhost:4502/bin/solrsearch?searchParameter=index&sitePath=/content/sumit/us/en -> TO INDEX
    //http://localhost:4502/bin/solrsearch?searchParameter=search&sitePath=/content/sumit/us/en&searchKey=prayagraj -> TO SEARCH
    private static final Logger LOG = LoggerFactory.getLogger(SolrSearchServlet.class);

    @Reference
    SolrServiceAPI solrServiceAPI;

    @Reference
    PageService pageService;

    @Reference
    SolrServiceManager solrServiceManager;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        LOG.info("--------Solr Search Start-------");

        SolrSearchHelper solrSearchHelper = new SolrSearchHelper(request,response,solrServiceManager);
        solrSearchHelper.searchSolr(pageService,solrServiceAPI);

        LOG.info("--------Solr Search End-------");
    }
}
