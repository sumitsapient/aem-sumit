package com.aem.sumit.core.services;

import com.aem.sumit.core.utils.ResourceResolverUtil;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.xmlbeans.impl.common.ResolverUtil;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

@Component(service = AemGeekServiceA.class)
public class AemGeekServiceAImpl implements AemGeekServiceA{

    @Reference
    ResourceResolverFactory resolverFactory;

    private static final Logger LOG = LoggerFactory.getLogger(AemGeekServiceAImpl.class);

    @Activate
    public void activate(ComponentContext context)
    {
    LOG.info("AemGeekServiceA Service Activated");
    LOG.info(context.getBundleContext().getBundle().getSymbolicName(),"\n {}");
    }

    @Override
    public Iterator<Page> getPages()  {

        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = ResourceResolverUtil.newResolver(resolverFactory);
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            Page page = pageManager.getPage("/content/sumit/us/en");
            Iterator<Page> child = page.listChildren();
            return child;
        } catch (LoginException e) {
            e.printStackTrace();
        }
        return null;
    }
}
