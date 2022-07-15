package com.aem.sumit.core.models;

import org.osgi.annotation.versioning.ConsumerType;

import java.util.Date;
import java.util.List;
import java.util.Map;


@ConsumerType
public interface BranchPageModel {


    /** @return String jcr:title */
    String getTitle();



    /** @return String source/locationType */
    String getLocationType();

}
