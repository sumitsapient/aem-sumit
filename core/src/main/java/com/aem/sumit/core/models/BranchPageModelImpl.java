package com.aem.sumit.core.models;


import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Objects;


@Model(adapters = BranchPageModel.class,
        adaptables = {Resource.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Slf4j
@Getter
public final class BranchPageModelImpl implements BranchPageModel {

    private static final String AIRPORT = "AIRPORT";
    private static final String PORT_OF_CALL = "PORT_OF_CALL";
    private static final String RAIL = "RAIL";

    @Getter(AccessLevel.NONE)
    @SlingObject
    private Resource currentResource;

    @SlingObject
    ResourceResolver resourceResolver;

    @ValueMapValue(name = "locationType")
    private String locationType;


    public String getTitle() {
        return title;
    }

    @ValueMapValue(name = "jcr:title")
    private String title;

    private String path;

    private PageManager pageManager;

    private Page page;

    @PostConstruct
    public void init() {
        pageManager = resourceResolver.adaptTo(PageManager.class);
        page = pageManager.getContainingPage(currentResource);
        title=getPriorityTitle(page);
        this.path = this.currentResource.getPath().replace("/jcr:content", "");
    }
    private String getPriorityTitle(Page page) {
        if(page == null){
            return StringUtils.EMPTY;
        }
        final String[] priorityOrderForTitle = new String[]{
                page.getNavigationTitle(),
                page.getPageTitle(),
                page.getTitle(),
                page.getName()};

        return Arrays.stream(priorityOrderForTitle).filter( Objects::nonNull).findFirst().orElse(StringUtils.EMPTY);
    }


}
