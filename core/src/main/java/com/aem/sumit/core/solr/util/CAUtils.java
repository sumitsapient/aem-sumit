package com.aem.sumit.core.solr.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for Context Aware Config.
 */
public class CAUtils {

    protected final static Logger LOGGER = LoggerFactory.getLogger(CAUtils.class);

    private CAUtils() {
    }

    public static <T>T getContextAwareConfig(final String currentPagePath, final ResourceResolver resourceResolver, final Class<T> clazz) {
        String currentPath = StringUtils.isNotBlank(currentPagePath) ? currentPagePath : StringUtils.EMPTY;
        Resource contentResource = resourceResolver.getResource(currentPath);
        if (contentResource != null) {
            ConfigurationBuilder configurationBuilder = contentResource.adaptTo(ConfigurationBuilder.class);
            if (configurationBuilder != null) {
                return configurationBuilder.as(clazz);
            }
        }
        return null;
    }

}