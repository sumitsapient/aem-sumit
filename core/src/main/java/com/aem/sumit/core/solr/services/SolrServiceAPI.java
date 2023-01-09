package com.aem.sumit.core.solr.services;

import com.aem.sumit.core.solr.helper.PageDetail;
import com.aem.sumit.core.solr.helper.SolrSearchHelper;

import java.util.List;

public interface SolrServiceAPI {

    public String addDocuments(List<PageDetail> pageDetails, SolrSearchHelper solrSearchHelper);
}
