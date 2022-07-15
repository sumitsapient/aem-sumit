package com.aem.sumit.core.servlets;

import com.aem.sumit.core.services.querybuilder.SearchService;
import com.aem.sumit.core.services.querybuilder.SearchServiceImpl;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletPaths(value = {"/geeks/search"})
public class SearchServlet extends SlingSafeMethodsServlet {

    //http://localhost:4502/geeks/search?searchText=women&pageNumber=1&resultPerPage=20
    private static final Logger LOG = LoggerFactory.getLogger(SearchServlet.class);

    @Reference
    SearchService service;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        JSONObject result = null;

        try {
            String text = request.getRequestParameter("searchText").toString();
            int pageNumber = Integer.parseInt(request.getRequestParameter("pageNumber").toString()) - 1;
            int resultPerPage = Integer.parseInt(request.getRequestParameter("resultPerPage").toString());
            int offset = pageNumber * resultPerPage;
            result = service.searchResult(text, offset, resultPerPage);
        } catch (Exception e) {
            LOG.info("Error {}", e.getMessage());
        }
        response.setContentType("application/json");
        response.getWriter().write(result.toString());

    }

}
