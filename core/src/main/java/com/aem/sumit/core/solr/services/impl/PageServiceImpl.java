package com.aem.sumit.core.solr.services.impl;


import com.aem.sumit.core.solr.helper.PageDetail;
import com.aem.sumit.core.solr.services.PageService;
import com.aem.sumit.core.utils.ResourceResolverUtil;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component(service = PageService.class , immediate = true)
public class PageServiceImpl implements PageService {

    private static final Logger LOG = LoggerFactory.getLogger(PageServiceImpl.class);
    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public List<PageDetail> getPagesDetail(String sitePath) {

        try{
            final ResourceResolver resourceResolver = ResourceResolverUtil.newResolver(resourceResolverFactory);
            Page page = resourceResolver.adaptTo(PageManager.class).getPage(sitePath);
            Iterator<Page> childPages = page.listChildren(null,true);
            List<PageDetail> pageDetailsList = new ArrayList<>();
            while (childPages.hasNext()) {
                Page child = childPages.next();
                String title = StringUtils.isNotBlank(child.getTitle()) ? child.getTitle() : child.getName();
                String name = child.getName();
                String description = StringUtils.isNotBlank(child.getDescription()) ? child.getDescription() : child.getName();
                String path = child.getPath();
                pageDetailsList.add(new PageDetail(title,name,description,path));
            }
                return pageDetailsList;
        }
        catch (Exception e) {
               LOG.error("ERROR GET- {}",e.getMessage());
        }
        return null;
    }

    @Override
    public PageDetail getSinglePageDetail(String pagePath) {
        try{
            final ResourceResolver resourceResolver = ResourceResolverUtil.newResolver(resourceResolverFactory);
            Page page = resourceResolver.adaptTo(PageManager.class).getPage(pagePath);
            String title = StringUtils.isNotBlank(page.getTitle()) ? page.getTitle() : page.getName();
            String name = page.getName();
            String description = StringUtils.isNotBlank(page.getDescription()) ? page.getDescription() : page.getDescription();
            String path = page.getPath();
            return new PageDetail(title,name,description,path);
        }
        catch (Exception e) {
            LOG.error("\n ERROR GET - {}", e.getMessage());
        }
        return null;
    }
}
