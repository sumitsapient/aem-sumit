package com.aem.sumit.core.utils;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import java.util.HashMap;
import java.util.Map;

public final class ResourceResolverUtil {

    public static  final String SUMIT_SERVICE_USER="sumitserviceuser";

    private ResourceResolverUtil() {
    }

    public static ResourceResolver newResolver(ResourceResolverFactory resolverFactory) throws LoginException {
        final Map<String , Object> hmap = new HashMap<>();
        hmap.put(ResourceResolverFactory.SUBSERVICE,SUMIT_SERVICE_USER);
        ResourceResolver resourceResolver = resolverFactory.getServiceResourceResolver(hmap);
        return resourceResolver;
    }
}
