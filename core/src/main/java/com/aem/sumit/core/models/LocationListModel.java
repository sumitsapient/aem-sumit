package com.aem.sumit.core.models;

import com.day.cq.wcm.api.Page;
import org.osgi.annotation.versioning.ConsumerType;

import java.util.List;
import java.util.Map;

/**
 * The interface Location list model.
 */
@ConsumerType
public interface LocationListModel {
    /** @return List<Page> topCitiesLocations. */
    List<Page> getTopCitiesLocations();

    /** @return true if no locations exists. */
    boolean isEmpty();

    Map<String,String> getTopLocationMap();
}
