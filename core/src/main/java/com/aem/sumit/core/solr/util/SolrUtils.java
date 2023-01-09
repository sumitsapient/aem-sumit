package com.aem.sumit.core.solr.util;

import com.aem.sumit.core.solr.services.impl.SolrServiceAPIImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrUtils {

    private static final Logger LOG = LoggerFactory.getLogger(SolrUtils.class);

    private static int socketTimeout = 6000;

    private static int connectionTimeout = 50000;

    public static SolrClient getSolrClient(String solrUrl) {
     try{
         return new HttpSolrClient.Builder(solrUrl)
                 .withConnectionTimeout(connectionTimeout)
                 .withSocketTimeout(socketTimeout)
                 .build();
     }
     catch (Exception e){
         LOG.error("\n Error in SolrUtil",e.getMessage());
     }
     return null;
    }

    public static String getPageName(String id) {
        String pageName = StringUtils.replaceAll(id,"/","-");
        return pageName;
    }
}
