package com.aem.sumit.core.services;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.LoginException;

import java.util.Iterator;
import java.util.List;

public interface AemGeekServiceA {
    public Iterator<Page> getPages();
}
