package com.aem.sumit.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = CaapContent.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = CaapContentImpl.RESOURCE_TYPE)
public class CaapContentImpl implements CaapContent{


    private static final Logger LOG = LoggerFactory.getLogger(CaapContentImpl.class);

    protected static final String RESOURCE_TYPE = "sumit/components/caap-content";


    @ValueMapValue
    private String carouselTag;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String summary;

    @ValueMapValue
    private String heading;

    @ValueMapValue
    private String fileReference;

    @ChildResource(name = "carouselitems")
    List<CarouselItem> items;



    @Override
    public String getCarouselTag() {
        return carouselTag;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSummary() {
        return summary;
    }

    @Override
    public String getHeading() {
        return heading;
    }

    @Override
    public String getFileReference() {
        return fileReference;
    }

    @Override
    public List<CarouselItem> getCarouselActive() {
        List<CarouselItem> active = items.stream().limit(3).collect(Collectors.toList());
        return active;
    }

    @Override
    public List<CarouselItem> getCarouselInactive() {
        return items;
    }


}
