package com.aem.sumit.core.models;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = CaapContent.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = CaapContentImpl.RESOURCE_TYPE)
public class CaapContentImpl implements CaapContent{

    private static final Logger LOG = LoggerFactory.getLogger(CaapContentImpl.class);

    protected static final String RESOURCE_TYPE = "sumit/components/caap-content";

    @ValueMapValue
    private String heading;

    @ChildResource(name = "carouselitems")
    List<CarouselItem> items;



    @Override
    public String getHeading() {
        return heading;
    }


    @Override
    public String getAllItem() {
        if(items.size()>0) {
            JSONArray carouselArray = new JSONArray(items);
            return carouselArray.toString();
        }
        return null;
    }


}
