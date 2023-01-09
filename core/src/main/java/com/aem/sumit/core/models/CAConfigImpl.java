package com.aem.sumit.core.models;


import com.aem.sumit.core.services.contextaware.GeeksCAConfig;
import com.day.cq.wcm.api.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Model(adaptables = SlingHttpServletRequest.class,
       adapters = {CAConfig.class},
       resourceType = {CAConfigImpl.RESOURCE_TYPE},
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class CAConfigImpl implements CAConfig  {

    private static final Logger LOG = LoggerFactory.getLogger(CAConfigImpl.class);
    public static final String RESOURCE_TYPE = "sumit/components/caware";

    @SlingObject
    ResourceResolver resourceResolver;

    @ScriptVariable
    Page currentPage;

    private String country;
    private String locale;
    private String admin;
    private String section;

    @PostConstruct
    public void init() {
        GeeksCAConfig caConfig = getContextAwareConfig(currentPage.getPath(),resourceResolver);
        country = caConfig.siteCountry();
        locale = caConfig.siteLocale();
        admin = caConfig.siteAdmin();
        section = caConfig.siteSection();
    }

    public GeeksCAConfig getContextAwareConfig(String currentPage , ResourceResolver resourceResolver) {
        String currentPath = StringUtils.isNotBlank(currentPage)?currentPage:StringUtils.EMPTY;
        Resource resource = resourceResolver.getResource(currentPath);
        if(resource!=null) {
            ConfigurationBuilder builder = resource.adaptTo(ConfigurationBuilder.class);
            if(builder!=null) {
                return builder.as(GeeksCAConfig.class);
            }
        }
        return null;
    }


    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getLocale() {
        return locale;
    }

    @Override
    public String getAdmin() {
        return admin;
    }

    @Override
    public String getSection() {
        return section;
    }
}
