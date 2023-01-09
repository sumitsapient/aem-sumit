package com.aem.sumit.core.solr.services;

import com.aem.sumit.core.solr.helper.PageDetail;

import java.util.List;

public interface PageService {

    public List<PageDetail> getPagesDetail(String sitePath);
}
