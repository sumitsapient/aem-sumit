package com.aem.sumit.core.solr.services;

import com.aem.sumit.core.solr.helper.PageDetail;
import com.aem.sumit.core.solr.helper.SolrSearchHelper;
import org.json.JSONArray;

import java.util.List;

public interface SolrServiceAPI {

    public String addDocuments(List<PageDetail> pageDetails, SolrSearchHelper solrSearchHelper);

    public JSONArray getSearchResult(String searchText, SolrSearchHelper solrSearchHelper);

    public void indexPage(SolrSearchHelper solrSearchHelper, PageDetail pageDetail);

    public void deletePage(SolrSearchHelper solrSearchHelper, PageDetail pageDetail);
}
